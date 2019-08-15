package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public interface Observer<T> {
    void onNext(T value);
    void onError(Throwable t);
}
