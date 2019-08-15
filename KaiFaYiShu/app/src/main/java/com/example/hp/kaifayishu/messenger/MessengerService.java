package com.example.hp.kaifayishu.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * @类名:MessengerService
 * @创建人:赵祖元
 * @创建时间：2018/10/4 0:25
 * @简述: 这是一套封装的AIDL的轻量级IPC方案
 */
public class MessengerService extends Service {

    //得到客户端消息处理
    public static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    break;
                default:
                    break;
            }

            Messenger client = msg.replyTo;  //拿到用来回复的Messenger对象
            Message message = Message.obtain();
            try {
                client.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
