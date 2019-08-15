package com.example.hp.kaifayishu.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.hp.kaifayishu.DiskLruCache;
import com.example.hp.kaifayishu.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:zuyuan
 * @date：2018/11/24
 * @note: 图片加载库 url并不一定是规范的文件名 将其转化成MD5格式
 */
public class ImageLoader {
    public static final String TAG = "ImageLoader";

    //消息的what
    public static final int MESSAGE_POST_RESULT = 1;

    //直接从网络上加载图片的what
    public static final int MESSAGE_POST_RESULT_HTTP = 2;

    //cup 核心数
    private static final int CUP_COUNT = Runtime.getRuntime().availableProcessors();

    //线程池的核心线程数
    private static final int CORE_POOL_SIZE = CUP_COUNT + 1;
    //线程池的最大线程数
    private static final int MAXIMUM_POOL_SIZE = CUP_COUNT * 2 + 1;
    //线程最长闲置时间
    private static final long KEEP_ALIVE = 10L;

    //ImageLoader 的标识符
    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    //最大缓存空间 100M
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 100;
    //BufferedOutputStream(BufferedInputStream) 中有效缓存的字节数
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    //一个磁盘文件描述符
    private static final int DISK_CHANGE_INDEX = 0;
    //是否是磁盘缓存创建的
    private boolean mIsDiskLruCacheCreated = false;

    //线程工厂 产生我们单独命名的线程
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    //直接配置自己的线程池
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory);

    //配置一个主线程的Handler
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_POST_RESULT) {
                LoaderResult result = (LoaderResult) msg.obj;
                ImageView imageView = result.imageView;
                String uri = (String) imageView.getTag(TAG_KEY_URI);
                if (uri.equals(result.url)) {
                    imageView.setImageBitmap(result.bitmap);
                } else {
                    Log.w(TAG, "set image bitmap, but url has changed, ignored!");
                }
            } else if (msg.what == MESSAGE_POST_RESULT_HTTP) {
                LoaderResult result = (LoaderResult) msg.obj;
                if (result.bitmap != null) {
                    result.imageView.setImageBitmap(result.bitmap);
                }
            }
        }
    };

    private Context mContext;
    //图片缩放处理
    private ImageResizer mImageResizer = new ImageResizer();
    //内存缓存
    private LruCache<String, Bitmap> mMemoryCache;
    //磁盘缓存
    private DiskLruCache mDiskLruCache;

    /**
     * 构造函数
     * 初始化内存缓存和磁盘缓存
     */
    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //最大内存 / 8
        int cacheSize = maxMemory / 10;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //行字节 * 高度 / 1024
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        //生成图片缓存文件
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            //创建目录
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(
                        diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建实例
     * @return
     */
    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    /**
     * 加入图片到内存
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }


    /**
     * 绑定到相应的ImageView上
     */
    public void bindBitmap(final String uri, final ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0 );
    }

    /**
     * 绑定到相应的ImageView上
     */
    public void bindBitmap(final String uri, final ImageView imageView,
                           final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        //先从内存中拿到
        //因为速度快，所以直接放入
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        //执行异步获取图片
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult loaderResult = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, loaderResult).sendToTarget();
                }
            }
        };
        //投递到线程池
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 遵从 先内存 再磁盘 再网络 的顺序
     * 并且会将图片依次加载到内存 磁盘中去
     * @return
     */
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache, uri : " + uri);
            return bitmap;
        }

        bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromDisk, url : " + uri);
            return bitmap;
        }

        bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromHttp, url : " + uri);
        }
        return bitmap;
    }

    /**
     * 根据对应的uri从内存中加载出图片
     * 因为加载的图片已经是缩放之后的图片，所以不用比较
     * @return
     */
    private Bitmap loadBitmapFromMemCache(String uri) {
        final String key = hashKeyFromUrl(uri);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    /**
     * 从本地磁盘中加载图片
     * 因为本地存的图片是原图，所以要比较，并且缩放
     * 如果加载成功则还要放入内存
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String uri, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "load bitmap from UI Thread, it's not commended!");
        }

        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFromUrl(uri);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null){
                //读取
                FileInputStream fileInputStream = (FileInputStream)snapshot
                        .getInputStream(DISK_CHANGE_INDEX);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                //缩放
                bitmap = mImageResizer.decodeSampleBitmapFromFileDescriptor(fileDescriptor,
                        reqWidth, reqHeight);
                if (bitmap != null) {
                    //添加到内存
                    addBitmapToMemoryCache(key, bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从网络获取图片
     * 顺序是先放入磁盘，再读取出图片文件
     * 在读取的过程中,又将图片放入了内存
     * @return
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can't visit network from UI Thread!");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        String key = hashKeyFromUrl(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(DISK_CHANGE_INDEX);
                //访问图片，并写入文件
                if (downLoadUrlToStream(url, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
            //再从磁盘中读取文件
            return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片对应的url生成其唯一的值 作为文件名
     * 获取md5值
     * @return
     */
    private String hashKeyFromUrl(String url) {
        String cacheKey ;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * 字节转成String
     * @return
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 根据key从LruCache中拿到Bitmap
     * @return
     */
    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 网络加载图片，并将输入流写入磁盘
     * @return
     */
    private boolean downLoadUrlToStream(String urlStr, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlStr);
            //网络请求
            urlConnection = (HttpURLConnection)url.openConnection();
            //读出流、写入流
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;

        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadBitmap failed." + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "io stream error." + e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null ) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 根据唯一路径创建一个文件
     * @param uniqueName 指定的唯一路径
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        //获取到外层的路径
        boolean externalStrorageAvailable = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStrorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        Log.d(TAG, "position-->" + cachePath + File.separator);
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取可用空间
     * @return
     */
    @SuppressLint("ObsoleteSdkInt")
    private long getUsableSpace(File path) {
        //大于版本9
        //过时版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSizeLong() *  statFs.getAvailableBlocksLong();
    }

    /**
     * 直接从网络上绑定ImageView
     */
    public void bindBitmapFromHttp(final ImageView imageView, final String url) {
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmapFromHttp(url);
                LoaderResult result = new LoaderResult(imageView, url, bitmap);
                mMainHandler.obtainMessage(MESSAGE_POST_RESULT_HTTP, result).sendToTarget();
            }
        };
        //投递到线程池
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 直接从网上加载图片
     * @return
     */
    public Bitmap loadBitmapFromHttp(String urlStr) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            Log.w(TAG, "load failed! -- MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.w(TAG, "load failed! -- IOException");
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * Message 传递的 obj
     */
    private class LoaderResult {

        LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.bitmap = bitmap;
            this.url = url;
        }

        String url;
        ImageView imageView;
        Bitmap bitmap;
    }
}