package com.example.hp.xiaogongtest.threethread;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @类名:ThreadChange
 * @创建人:赵祖元
 * @创建时间：2018/8/1 17:07
 * @简述: wait()、sleep()以及RunnableFuture
 */
public class ThreadChange {

    private static Object sLockObject = new Object();

    public static void main(String[] args){
        System.out.println("主线程运行");

        Thread thread = new WaitThread();
        thread.start();

        Long startTime = System.currentTimeMillis();

        try {
            synchronized (sLockObject){
                System.out.println("主线程等待");
                sLockObject.wait();
            }
        } catch (InterruptedException e) {
        }

        Long timeMs = (System.currentTimeMillis() - startTime);
        System.out.println("主线程继续等待，持续时间：" + timeMs + "ms");

        //一匹可控制的“战马”
        new Thread(new RunnableFuture<Integer>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Integer get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public Integer get(long timeout, @NonNull TimeUnit unit) throws InterruptedException
                    , ExecutionException, TimeoutException {
                return null;
            }

            @Override
            public void run() {

            }
        });

    }

    static class WaitThread extends Thread{
        @Override
        public void run() {
            synchronized (sLockObject){
                try {
                    Thread.sleep(2000);
                    sLockObject.notify();       //唤醒

                } catch (InterruptedException e) {

                }
            }
        }
    }
}
