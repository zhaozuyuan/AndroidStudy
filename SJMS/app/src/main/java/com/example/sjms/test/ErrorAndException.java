package com.example.sjms.test;

/**
 * create by zuyuan on 2019/5/31
 */
public class ErrorAndException {

    public static void main(String[] args) {
        //error();
        exception();
    }

    static class A{ }
    static class B{ }

    /**
     * 非检查异常
     */
    private static void error() {
        Object o = new A();
        //类型转化异常
        B b = (B) o;
    }


    /**
     * 检查异常
     */
    private static void exception() {
        A a = null;
        try {
            a.toString();
        }catch (NullPointerException e) {
            System.out.println("空指针异常");
        }
    }

}
