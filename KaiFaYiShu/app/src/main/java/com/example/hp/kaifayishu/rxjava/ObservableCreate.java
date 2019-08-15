package com.example.hp.kaifayishu.rxjava;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author:zuyuan
 * @date：2018/11/27
 * @note:
 */
public class ObservableCreate<T> extends Observable<T> {
    final ObservableOnSubscrible source;

    public ObservableCreate (ObservableOnSubscrible<T> source) {
        System.out.println("create --> ObservableCreate");
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        //observer.onSubscribe();
        System.out.println("create -->CreateEmitter");
        CreateEmitter<T> parent = new CreateEmitter<>(observer);
        try {
            source.subscrible(parent);
        }catch (Exception e) {
            parent.onError(new Throwable());
        }
    }

    /**
     * @param <T>
     */
    static final class CreateEmitter<T> extends AtomicReference<Disposable>
            implements ObservableEmitter<T>, Disposable {

        final Observer<? super  T> observer;


        CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T result) {
            if (!isDisposed()) {
                observer.onNext(result);
            }
        }

        @Override
        public void onError(Throwable t) {
            observer.onError(t);
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposable(get());
        }
    }
}
