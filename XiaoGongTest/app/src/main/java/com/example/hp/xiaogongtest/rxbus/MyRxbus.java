package com.example.hp.xiaogongtest.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @类名:MyRxBus
 * @创建人:赵祖元
 * @创建时间：2018/8/15 20:14
 * @简述: 没有被压处理的Rxbus,请注意若是要有背压的Rxbus：
 * 将Observable改成Flowable，将Observable改成Subscribers即可。
 *
 * 根据RxJava的设计原则，在处理事件出现异常之后，订阅者将无法再收到事件。
 */
public class MyRxbus {
    private final Subject<Object> mBus;

    public MyRxbus(){
        mBus = PublishSubject.create().toSerialized();
    }

    public static MyRxbus get(){
        return MyHolder.BUS;
    }

    public void post(Object object){
        mBus.onNext(object);
    }

    public <T>Observable<T> toObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable(){
        return mBus;
    }

    public boolean hasObservers(){
        return mBus.hasObservers();
    }

    private static class MyHolder{
        private static final MyRxbus BUS = new MyRxbus();
    }

}
