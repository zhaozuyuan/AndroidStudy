package com.example.hp.kaifayishu.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hp.kaifayishu.R;

public class ShowViewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_view);

        init();
    }

    private void init() {
        Button button = (Button)findViewById(R.id.btn_remote_popup);
        button.setOnClickListener(this);
    }

    private void popupRemoteView() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setContentText("hello world")
                .setContentTitle("Notification")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lemon))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setWhen(System.currentTimeMillis())
                .build();
        assert manager != null;
        manager.notify(1, notification);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_remote_popup:
                popupRemoteView();
                break;
        }
    }
}
