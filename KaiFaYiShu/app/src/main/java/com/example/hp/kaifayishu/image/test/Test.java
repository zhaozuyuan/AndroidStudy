package com.example.hp.kaifayishu.image.test;

import android.os.SystemClock;

import java.lang.ref.WeakReference;

/**
 * @author:zuyuan
 * @date：2018/11/24
 * @note:
 */
public class Test {

    public static void main(String[] args) {
        WeakReference<A> aWeakRef = new WeakReference<>(new A());
        System.out.println("a weak reference ---> " + (aWeakRef.get() == null
                ? "null" : aWeakRef.get()));
        System.gc();
        System.out.println("a weak reference ---> " + (aWeakRef.get() == null
                ? "null" : aWeakRef.get()));

        WeakReference<A> aWeakRef1 = new WeakReference<>(new A());
        B b = new B(aWeakRef1.get());
        System.out.println("a weak reference ---> " + (aWeakRef1.get() == null
                ? "null" : aWeakRef1.get()));
        System.gc();
        System.out.println("a weak reference ---> " + (aWeakRef1.get() == null
                ? "null" : aWeakRef1.get()));


        final Test t = new Test();
        System.out.println("current time -->" + System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.aVoid();
            }
        }).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.aVoid();
        System.out.println("current time -->" + System.currentTimeMillis());
    }

    public synchronized void aVoid() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
