package com.example.hp.kaifayishu.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * @author:zuyuan
 * @date：2018/11/24
 * @note: 拿到符合条件的缩放之后的Bitmap
 */
public class ImageResizer {
    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId,
                                                 int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //开关，允许在解码过程中的修改
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //设置缩放的比例（像素点的多少）
        options.inSampleSize = calculateInSample(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd,
                                                        int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSample(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 计算需要采样缩放的比例
     * @return
     */
    public int calculateInSample(BitmapFactory.Options options, int reqWidth,
                                        int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //直到符合条件为止
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
