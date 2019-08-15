package com.example.hp.kaifayishu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hp.kaifayishu.R;

public class ActivityA extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.layout_right_in, R.anim.layout_right_out);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
        overridePendingTransition(R.anim.layout_scale_0_1, R.anim.normal);
    }
}
