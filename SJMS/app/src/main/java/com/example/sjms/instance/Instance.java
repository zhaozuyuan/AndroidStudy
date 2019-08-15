package com.example.sjms.instance;

/**
 * create by zuyuan on 2019/5/19
 */
public class Instance {

    /**
     * 懒汉模式
     * 每次获取单例的时候都要加锁，造成不必要的同步开销
     */
    public static class Singleton1 {
        private static Singleton1 instance;
        private Singleton1() {}

        public static synchronized Singleton1 getInstance() {
            if (instance == null) instance = new Singleton1();
            return instance;
        }
    }

    /**
     * 双重检验锁，JDK1.5以下，高并发会失效
     * JDK1.5以上 volatile 保证每次都从主存中读取
     */
    public static class Singleton2 {
        private static Singleton2 instance;
        private Singleton2() {}

        public static Singleton2 getInstance() {
            if (instance == null) {
                synchronized (Singleton2.class) {
                    if (instance == null) {
                        instance = new Singleton2();
                    }
                }
            }
            return instance;
        }
    }
}
