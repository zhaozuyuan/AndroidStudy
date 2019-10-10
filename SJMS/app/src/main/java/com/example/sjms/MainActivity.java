package com.example.sjms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = new TextView(getApplicationContext());
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        view.setText("吃鸭儿");
        view.setTextSize(20);
        ViewGroup vg = findViewById(R.id.fl);
        vg.addView(view);

        EventBus.builder().addIndex(new MyEventBusIndex());
        EventBus.getDefault().register(this);

        SixPrinciple sixPrinciple = null;
        EventBus.builder()
                .ignoreGeneratedIndex(false)
                .installDefaultEventBus();
        EventBus.getDefault().post(sixPrinciple);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRecivedSixPrinciple(SixPrinciple sixPrinciple) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
