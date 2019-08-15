package com.example.hp.xiaogongtest.fivesql;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @类名:DbCommmand
 * @创建人:赵祖元
 * @创建时间：2018/8/14 20:49
 * @简述:
 */
public abstract class DbCommand<T> {
    //只有一个线程的线程池
    private static ExecutorService sDbService = Executors.newSingleThreadExecutor();

    //UI的Handler
    private final static Handler sUIHandler = new Handler(Looper.getMainLooper());

    /**
     * 执行数据库操作
     */
    public final void execute(){
        sDbService.execute(new Runnable() {
            @Override
            public void run() {
                postResult(doInBackground());  //请注意，这里是是在线程池执行后在给它去执行0
            }
        });
    }

    private void postResult(final T result){
        sUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);

                doInBackground();


            }
        });
    }

    /**
     * 后台执行数据库操作
     *
     * @return
     */
    public abstract T doInBackground();

    /**
     * 结果投递到UI
     *
     * @param result
     */
    public abstract void onPostExecute(T result);
}
