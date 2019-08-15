package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public abstract class Observable<T> {

    public static <T>Observable<T> create(ObservableOnSubscrible<T> source) {
        return RxJavaPlugins.onAssembly(new ObservableCreate<>(source));
    }

    public final Disposable subscribe(Consumer<? super T> value) {
        return this.subscribe(value, null);
    }

    public final Disposable subscribe(Consumer<? super T> value,
                                      Consumer<? super Throwable> t) {
        LambdaObserver ls = new LambdaObserver(value, t);

        subscribe(ls);

        return ls;
    }


    public final void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);
}
