package com.example.hp.kaifayishu;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hp.kaifayishu.activity.ActivityA;
import com.example.hp.kaifayishu.image.ImageActivity;
import com.example.hp.kaifayishu.tcp.TcpClientActivity;
import com.example.hp.kaifayishu.widget.Rotate3dAnimation;
import com.example.hp.kaifayishu.widget.ShowViewActivity;
import com.example.hp.kaifayishu.window.ARelativeLayout;
import com.example.hp.kaifayishu.window.WindowActivity;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int n = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (false) {
            startActivity(new Intent());
        }

        init();
    }

    private void init() {
        initButton();
    }

    private void initButton() {
        findViewById(R.id.btn_to_tcp_activity).setOnClickListener(this);
        findViewById(R.id.btn_to_drag_view_activity).setOnClickListener(this);
        findViewById(R.id.btn_to_show_view_activity).setOnClickListener(this);
        findViewById(R.id.btn_to_activity_a).setOnClickListener(this);
        findViewById(R.id.btn_to_activity_window).setOnClickListener(this);
        findViewById(R.id.btn_to_image_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_tcp_activity:
                startActivity(new Intent(this, TcpClientActivity.class));
                break;
            case R.id.btn_to_drag_view_activity:
                startActivity(new Intent(this, DragViewActivity.class));
            case R.id.btn_to_show_view_activity:
                startActivity(new Intent(this, ShowViewActivity.class));
            case R.id.btn_to_activity_a:
                startActivity(new Intent(this, ActivityA.class));
                overridePendingTransition(R.anim.layout_left_in,
                        R.anim.layout_left_out);
                break;
            case R.id.btn_to_activity_window:
                startActivity(new Intent(this, WindowActivity.class));
                overridePendingTransition(R.anim.layout_left_in,
                        R.anim.layout_left_out);
                break;
            case R.id.btn_to_image_activity:
                startActivity(new Intent(this, ImageActivity.class));
            default:
                break;
        }
    }

    public void imageOnClick(View view) {
        Rotate3dAnimation animation = new Rotate3dAnimation(0f, 1.0f,
                0f, 0f, 1.0f, false);
        view.startAnimation(animation);
    }

    public void stateBtnOnClick(View view) {
        if (!view.isFocusableInTouchMode()) {
            //设置可聚焦触摸模式
            view.setFocusableInTouchMode(true);
            //申请焦点
            view.requestFocus();
        } else {
            Log.d("TAG", "clear");
            view.setFocusableInTouchMode(false);
            view.clearFocus();
        }
    }
}
