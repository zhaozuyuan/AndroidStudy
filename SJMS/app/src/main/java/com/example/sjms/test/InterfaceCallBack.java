package com.example.sjms.test;

/**
 * create by zuyuan on 2019/5/31
 */
public class InterfaceCallBack {

    public interface CallBack {
        void onResult(String data);
    }

    public static void main(String[] args) {
        B b = new B();
        A a = new A(b);
        C c = new C(b);
        a.start();
        c.start();
    }

    public static class A {
        private B mB;

        public A(B b) {
            mB = b;
        }

        public void start() {
            mB.request(new CallBack() {
                @Override
                public void onResult(String data) {
                    A.this.onResult(data);
                }
            });
        }

        public void onResult(String data) {
            System.out.println("A收到结果" + data);
        }
    }

    public static class C {
        private B mB;

        public C(B b) {
            mB = b;
        }

        public void start() {
            mB.request(new CallBack() {
                @Override
                public void onResult(String data) {
                    C.this.onResult(data);
                }
            });
        }

        public void onResult(String data) {
            System.out.println("C收到结果" + data);
        }
    }

    public static class B {
        public void request(CallBack callBack) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        callBack.onResult("成功！");
                    } catch (InterruptedException e) {
                        callBack.onResult("失败！");
                    }
                }
            }).start();
        }
    }

}
