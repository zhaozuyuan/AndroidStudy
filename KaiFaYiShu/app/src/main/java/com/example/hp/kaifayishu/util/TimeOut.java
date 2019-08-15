package com.example.hp.kaifayishu.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.hp.kaifayishu.tcp.MyHandler;

/**
 * @author:zuyuan
 * @date：2018/10/13
 * @note:
 */
public final class TimeOut {

    private Handler mHandler ;

    private int mWhat;

    private Looper getLooper() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            Looper.prepare();
            looper = Looper.myLooper();
        }

        return looper;
    }

    public void startTiming(int time, Handler.Callback callBack) {
        mHandler = new Handler(getLooper(), callBack);
        Message msg = Message.obtain(mHandler);
        mWhat = msg.hashCode();
        msg.what = mWhat;
        mHandler.sendMessageDelayed(msg, time);
    }

    public void cancel() {
        if (mHandler != null) {
            mHandler.removeMessages(mWhat);
        }
    }
}
