package com.example.hp.xiaogongtest.threethread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @类名:ExecutorDemo
 * @创建人:赵祖元
 * @创建时间：2018/8/2 15:24
 * @简述: 启动了含有三个线程的线程池
 */
public class ExecutorDemo {
    public static final int MAX = 10;

    public static void main(String[] args){
        try {
            long startTime = System.currentTimeMillis();

            fixedThreadPool(2,startTime);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void fixedThreadPool(int size,long startTime)throws CancellationException
            ,ExecutionException,InterruptedException{

        //创建固定数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(size);

        for(int i = 0;i < MAX;i++){
            Future<Integer> task = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("执行线程（当前线程名称）："+Thread.currentThread().getName());
                    return fibc(40);
                }
            });

            //获取结果
            System.out.println("第 "+ i +" 次计算，结果："+ task.get());

            long ATime = System.currentTimeMillis();
            System.out.println("第 "+ i +" 次计算，时间相隔："+ (ATime-startTime));
        }
    }

    //效率低下的斐波那契数列，耗时的操作
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
