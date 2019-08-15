package com.example.hp.xiaogongtest.threethread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * @类名:SimpleAsyncTask
 * @创建人:赵祖元
 * @创建时间：2018/8/3 9:38
 * @简述:
 */
public abstract class SimpleAsyncTask<T>{

    //这个类里封装了自己的Thread和Handler
    private static final HandlerThread HT = new HandlerThread("SimpleAsyncTask"
            , Process.THREAD_PRIORITY_BACKGROUND);

    static {
        HT.start();
    }

    //ui的handler
    final Handler mUIHandler = new Handler (Looper.getMainLooper());

    //子线程的handler
    final Handler mAsyncTaskHandler = new Handler(HT.getLooper());


    /**
     * @功能描述：执行任务之前的初始化操作
     */
    protected abstract void onPreExecute();

    /**
     * 后台执行任务
     *
     * @return 返回结果
     */
    protected abstract T doInBackground();

    /**
     * 接收 doInBackground传递的结果
     *
     * @param result
     */
    protected abstract void onPostExecute(T result);

    /**
     * 在子线程做任务
     *
     * @return 返回结果
     */
    public final SimpleAsyncTask<T> execute(){
        onPreExecute();

        mAsyncTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                postResult(doInBackground());
            }
        });

        return this;
    }

    /**
     * 将结果在ui线程执行
     *
     * @param result
     */
    private void postResult(final T result){
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }
}
