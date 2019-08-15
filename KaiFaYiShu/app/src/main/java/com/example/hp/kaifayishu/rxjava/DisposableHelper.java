package com.example.hp.kaifayishu.rxjava;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author:zuyuan
 * @date：2018/11/28
 * @note: 取消订阅的帮助类
 */
public enum DisposableHelper implements Disposable {
    DISPOSED;

    @Override
    public void dispose() {
        return ;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    public static boolean isDisposable(Disposable d) {
        return d == DISPOSED;
    }

    public static boolean dispose(AtomicReference<Disposable> field) {
        Disposable current = field.get();
        Disposable d = DISPOSED;
        if (current != d) {
            current = field.getAndSet(d);
            if (current != d) {
                if (current != null) {
                    current.dispose();
                }
                return true;
            }
        }
        return false;
    }
}
