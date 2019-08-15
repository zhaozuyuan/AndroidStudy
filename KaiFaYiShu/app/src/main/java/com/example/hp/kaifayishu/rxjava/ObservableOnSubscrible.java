package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public interface ObservableOnSubscrible<T> {
    void subscrible(ObservableEmitter<T> emitter);
}
