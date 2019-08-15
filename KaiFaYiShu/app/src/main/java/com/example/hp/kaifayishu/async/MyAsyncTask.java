package com.example.hp.kaifayishu.async;

import android.os.AsyncTask;

/**
 * @author:zuyuan
 * @date：2018/11/19
 * @note:
 */
public class MyAsyncTask extends AsyncTask {
    /**
     * 返回结果，在主线程执行
     * @param o
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    /**
     * 进度条刷新，在主线程执行
     * @param values
     */
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    /**
     * 在主线程执行，在异步执行执行前调用
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 异步执行方法
     * @param objects
     * @return
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        //调用publishProgress(); 执行onProgressUpdate(); 刷新进度条方法
        return null;
    }

    /**
     * 异步任务被取消时调用（子线程），这个时候的onPostExecute() 将不再调用
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
