package com.example.hp.xiaogongtest.twocostomview.other;

/**
 * @类名:Aaa
 * @创建人:赵祖元
 * @创建时间：2018/8/6 14:03
 * @简述:
 */
public class Aaa  {
    static {
        System.out.println("外部类静态代码块");
    }

    static class AAA
    {
        public static int s ;

        public static int aaa(){
            System.out.println("静态内部类静态方法");
            s = 1;
            return s;
        }

        public AAA(){
            System.out.println("静态内部类实例");
        }
    }

    public static int bbb(){
        return AAA.aaa();
    }

    public void s(){}
}
