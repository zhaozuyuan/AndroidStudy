package com.example.hp.xiaogongtest.twocostomview.other;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.hp.xiaogongtest.twocostomview.other.Aaa;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @类名:Show
 * @创建人:赵祖元
 * @创建时间：2018/8/6 14:03
 * @简述:
 */
public class Show {
    public Object object = new Object();

    public static int i =2;
    public static void main(String[] arg){
       System.out.println(Aaa.bbb());
       //System.out.println( Show.i);

       // System.out.println("");

        //Aa a = new Aa();
      //  System.out.println(a.s);

        System.out.println("");

        Aaa aaa = new Aaa();
        aaa.s();

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    public void a(){
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<Integer> task = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });

        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void aaa(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();

                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };
}
