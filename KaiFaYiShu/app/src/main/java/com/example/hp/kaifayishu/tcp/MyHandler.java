package com.example.hp.kaifayishu.tcp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Modifier;

/**
 * @author:zuyuan
 * @date：2018/10/8
 * @note:
 */
public class MyHandler {

    private static final String TAG = "Handler";

    //是否可能的泄漏
    private static final boolean FIND_POTENTIAL_LEAKS = false ;
    //主线程的handler
    private static MyHandler MAIN_THREAD_HANDLER = null;

    //接口回调
    private CallBack mCallBack = null;
    //当前线程的looper
    private Looper mLooper ;
    //该线程的消息队列,从Looper中拿到
    private MessageQueue mQueue;
    //是否异步
    private boolean mAsynchronous;

    /**
     * 接口回调，接收消息
     */
    public interface CallBack {
        /**
         * 拥有返回值的 "处理消息"
         * @param msg
         * @return 将消息继续传递
         */
        public boolean handleMessage(Message msg);
    }

    /**
     * 专门被覆写的方法
     * 回调
     * @param msg
     */
    public void handleMessage(Message msg){
    }

    /**
     * 接收到消息，分发消息
     * msg.getCallBack() 是其执行的事件(Runnable)
     * 这个runnable 其实是自己在Message.obtain() 时传入的参数
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        if (msg.getCallback() != null) {    //runnable事件不为空
            handleMessage(msg);
        } else {
            if (mCallBack != null) {
                if (mCallBack.handleMessage(msg)) return;
            }
            handleMessage(msg);
        }
    }

    public MyHandler() {this(null, false);}

    public MyHandler(CallBack callBack) {this(callBack, false);}

    public MyHandler(Looper looper) {this(looper, null, false);}

    public MyHandler(Looper looper, CallBack callBack) {this(looper, callBack, false);}

    public MyHandler(boolean async) {this(null, async);}

    /**
     * 实现构造器1
     * @param callBack 接口回调
     * @param async 是否异步
     */
    @SuppressLint("NewApi")
    public MyHandler(CallBack callBack, boolean async) {
        if (FIND_POTENTIAL_LEAKS) {
            //实例MyHandler的时候是按照匿名类、成员类、内部类 实现的
            //并且非static （这就非常容易出现内存泄漏，它持有外部类对象）
            final Class<? extends MyHandler> klass = getClass();
            if ((klass.isAnonymousClass() || klass.isMemberClass() || klass.isLocalClass()) &&
                    (klass.getModifiers() & Modifier.STATIC) == 0) {
                Log.w(TAG, "The following MyHandler class should be static or leaks might occur:" +
                        klass.getCanonicalName());
            }
        }

        //从ThreadLocal 中拿到当前线程的looper
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    //指出当前线程没有初始化looper
                    "Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.getQueue();
        mCallBack = callBack;   //设置为传入的callback
        mAsynchronous = async;  //设置异步
    }

    /**
     * 实现构造器2
     * 一般用于原生子线程，需要手动绑定looper
     * @param looper
     * @param callBack
     * @param async
     */
    @SuppressLint("NewApi")
    public MyHandler(Looper looper, CallBack callBack, boolean async) {
        mLooper = looper;
        mQueue = looper.getQueue();
        mCallBack = callBack;
        mAsynchronous = async;
    }

    /**
     * @return 得到主线程的handler
     */
    public static MyHandler getMain() {
        if (MAIN_THREAD_HANDLER == null) {
            MAIN_THREAD_HANDLER = new MyHandler(Looper.getMainLooper());
        }
        return MAIN_THREAD_HANDLER;
    }

    /**
     * @param handler
     * @return
     */
    public static MyHandler mainIfNull(@Nullable MyHandler handler) {
        return handler == null ? getMain() : handler;
    }

    /**
     * 获取跟踪名字
     * @param message
     * @return
     */
    public String getTraceName(Message message) {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(": ");
        if (message.getCallback() != null) {
            sb.append(message.getCallback().getClass().getName());
        } else {
            sb.append("#").append(message.what);
        }
        return sb.toString();
    }

    /**
     * @param message
     * @return 事件名字
     */
    public String getMessageName(Message message) {
        if (message.getCallback() != null) {
            return message.getCallback().getClass().getName();
        } else {
            //十六进制
            return "0x" + Integer.toHexString(message.what);
        }
    }

    public final Message obtainMessage() {
        return Message.obtain(new Handler());    //此处应该是obtain(handler)
    }

    /**
     * @param what
     * @return 全局消息池的Message
     */
    public final Message obtainMessage(int what) {
        return Message.obtain(new Handler(), what);
    }

    /**
     * 发射事件到消息队列,事件在此线程中执行
     * 在looper被删除之后，此条消息也就被删除
     * @param r
     * @return 是否放入队列成功
     */
    public final boolean post(Runnable r) {
        //return sendMessageDelayed(,0)
        return false;
    }

    /**
     * 放入消息队列，线程睡眠，指定时间执行此事件
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        //TODO
        return false;
    }

    public final boolean postAtTime(Runnable r, Object token, long uptimeMillis) {
        //TODO
        return false;
    }

    /**
     * 放入消息队列，延迟时间执行
     * 最后同样执行的是atTime
     */
    public final boolean postDelayed(Runnable r, long delayMills) {
        //TODO
        return false;
    }

    /**
     * 将事件放到消息队列的最前面
     * @param r
     * @return
     */
    public final boolean postAtFrontOfQueue(Runnable r) {
        return false;
    }

    /**
     * 同步执行任务
     * 当前线程直接执行
     * 其它线程需要等待，容易造成死锁
     * 一般不采用该方法
     * @param r
     * @param timeout
     * @return
     */
    public final boolean runWithScissors(final  Runnable r, long timeout) {
        if (r == null) {
            throw new IllegalArgumentException("runnable must not be null");
        }
        if (timeout < 0){
            throw new IllegalArgumentException("timeout must be non-negative");
        }

        //是否是当前线程的looper
        if (Looper.myLooper() == mLooper) {
            r.run();
            return true;
        }

        return false;
    }

    /**
     * 删除消息
     * @param r
     */
    public final void removeCallbacks(Runnable r) {
        // mQueue.removeMessages()
    }

    public final void removeCallbacks(Runnable r, Object token) {
    }

    /**
     * 发送消息
     * @param msg
     * @return
     */
    public final boolean sendMessage(Message msg) {
        return sendMessageDelayed(msg, 0);
    }

    public final boolean sendEmptyMessage(int what) {
        return sendEmptyMessageDelayed(what, 0);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageDelayed(msg, 0);
    }

    public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageAtTime(msg, uptimeMillis);
    }

    /**
     * 延迟发送消息 最终调用仍然是 AtTime
     * @param msg
     * @param delayMillis
     * @return
     */
    public final boolean sendMessageDelayed(Message msg, long delayMillis){
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMills) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(this + "sendMessageAtTime() called with no " +
                    "mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMills);
    }

    /**
     * 将消息排在消息队列的最前面
     * 容易造成排序问题
     * @param msg
     * @return
     */
    public final boolean sendMesasageAtFrontOfQueue(Message msg) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(this + "sendMessageAtTime() called with no " +
                    "mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, 0);
    }

    /**
     * 将消息投入该消息队列
     * @param queue
     * @param msg
     * @param uptimeMillis
     * @return
     */
    @SuppressLint("NewApi")
    private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        //msg.getTarget() = this;
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        // return queue.enqueueMessage(msg, uptimeMillis);
        return true;
    }

    public final void removeMessages(int what) {
        //mQueue.removeMessages(this, what, null);
    }

    /*
      提供一些查询Message 或者 CallBack 的方法
     */
    public final Looper getLooper() {
        return mLooper;
    }

    /**
     * @param r
     * @param token
     * @return 要投递的消息
     */
    public static Message getPostMessage(Runnable r, Object token) {
        Message m = Message.obtain();
        m.obj = token;
        //m.callback = r;
        return m;
    }

    private static final class BlockingRunnable implements Runnable{
        private final Runnable mTask;
        private boolean mDone;

        public BlockingRunnable(Runnable task) {mTask = task;}

        @Override
        public void run() {
            mTask.run();
            synchronized (this) {
                mDone = true;
                notifyAll();    //唤醒线程
            }
        }

        /**
         * 线程休眠 直到投递事件完成
         * @param handler
         * @param timeout
         * @return
         */
        public boolean postAndWait(MyHandler handler, long timeout) {
            if (!handler.post(this)) {
                return false;
            }

            synchronized (this) {
                if(timeout > 0) {
                    final long expirationTime = SystemClock.uptimeMillis() + timeout;
                    while (!mDone) {
                        long delay = expirationTime - SystemClock.uptimeMillis();
                        if (delay > 0) {
                            return false;
                        }
                        try {
                            wait(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }


}
