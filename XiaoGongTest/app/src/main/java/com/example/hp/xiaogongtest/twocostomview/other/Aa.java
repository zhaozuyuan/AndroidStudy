package com.example.hp.xiaogongtest.twocostomview.other;

/**
 * @类名:Aa
 * @创建人:赵祖元
 * @创建时间：2018/8/6 14:03
 * @简述:
 */
public class Aa extends A{
    static {
        System.out.println("子类静态代码块");
    }

    static String s = "子类的静态变量";

    public Aa(){
        System.out.println("子类构造函数");
    }
}
