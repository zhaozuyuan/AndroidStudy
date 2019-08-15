package com.example.hp.xiaogongtest.threethread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @类名:FutureDemo
 * @创建人:赵祖元
 * @创建时间：2018/8/2 15:57
 * @简述: Future模式例子
 *
 * Runnable的run()是没有返回值的，只是执行完逻辑，而另外两个则具有返回值。
 * 然而最终都是在其内部通过callable的形式实现的。
 */
public class FutureDemo {
    //得到一个线程池
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args){
        try {
            futureWithRunnable();
            futureWithCallable();
            futureTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //向线程池中提交Runnable对象
    private static void futureWithRunnable() throws InterruptedException,
            ExecutionException {

        //提交runnable，没有返回值，future没有数据。
        //在内部也是执行的callable
        Future<?> result = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                fibc(20);
            }
        });

        System.out.println("future result from runnable："+result.get());
}

    //向线程池里面提交Callable对象，有返回值
    private static void futureWithCallable() throws InterruptedException,
            ExecutionException{

        //提交callable，有返回值，future有数据。
        Future<Integer>result2 = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });

        System.out.println("future result from callable："+result2.get());
    }

    //提交FutureTask对象
    private static void futureTask() throws InterruptedException, ExecutionException{

        //提交futureTask对象
        Future<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });

        mExecutor.submit((FutureTask)futureTask);

        System.out.println("future result from futureTask："+futureTask.get());
    }

    //耗时的一个操作
    private static int fibc(int num){
        if (num == 0){
            return 0;
        }
        if (num == 1){
            return 1;
        }

        return fibc(num - 1) + fibc(num - 2);
    }

}
