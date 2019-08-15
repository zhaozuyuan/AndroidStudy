package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note: Consumer的最终封装
 */
public class LambdaObserver<T> implements Disposable, Observer<T>{

    private final Consumer<? super T> onNext;
    private final Consumer<? super Throwable> onError;

    public LambdaObserver(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        System.out.println("create -->LambadaObserver");
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void onNext(T value) {
        onNext.accept(value);
    }

    @Override
    public void onError(Throwable t) {
        onError.accept(t);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDisposed() {
        return false;
    }
}
