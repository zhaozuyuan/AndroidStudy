package com.example.sjms.factory.common.net;

/**
 * create by zuyuan on 2019/7/19
 */
public interface INetHelper {

    /**
     * 这不是一个泛型方法，泛型方法是在调用时才确定类型的方法
     */
    void request(CallBack callBack);

    interface CallBack {
        void onResult(String result);
    }
}
