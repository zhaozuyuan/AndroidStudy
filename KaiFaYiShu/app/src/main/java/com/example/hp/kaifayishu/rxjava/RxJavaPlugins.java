package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public class RxJavaPlugins {
    public static <T>Observable<T> onAssembly(Observable<T> source) {
        return source;
    }

    public static void onError(Throwable t) {
        //TODO
    }
}
