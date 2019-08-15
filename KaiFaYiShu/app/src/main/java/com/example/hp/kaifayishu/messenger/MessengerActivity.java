package com.example.hp.kaifayishu.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.kaifayishu.R;

public class MessengerActivity extends AppCompatActivity {

    private Messenger mService;

    private class ReplyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //
        }
    }

    private Messenger reply = new Messenger(new Handler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);  //拿到服务对象
            Message message = Message.obtain(null, 1);
            //这一句添加上了客户端的回应
            message.replyTo = reply;
            Bundle data = new Bundle();
            //这一句则是客户端的数据
            data.putString("key", "string");
            message.setData(data);
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
