package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public interface ObservableEmitter<T> {
    void onNext(T result);
    void onError(Throwable t);
    boolean isDisposed();
}
