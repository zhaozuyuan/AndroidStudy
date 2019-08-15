package com.example.hp.kaifayishu.rxjava;

/**
 * @author:zuyuan
 * @date：2018/11/28
 * @note:
 */
public class Test {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscrible<Integer>() {
            @Override
            public void subscrible(ObservableEmitter<Integer> emitter) {
                System.out.println("send 1");
                emitter.onNext(1);
                emitter.onError(new Throwable());
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("receive " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                System.out.println("receive error");
            }
        });
    }
}
