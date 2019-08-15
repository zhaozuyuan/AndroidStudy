package com.example.hp.xiaogongtest.twocostomview.other;

/**
 * @类名:A
 * @创建人:赵祖元
 * @创建时间：2018/8/6 14:03
 * @简述:
 */
public class A {
    static {
        System.out.println("父类静态代码块");
    }

    static String i = "A";

    public A(){
        System.out.println("父类构造函数");
    }
}
