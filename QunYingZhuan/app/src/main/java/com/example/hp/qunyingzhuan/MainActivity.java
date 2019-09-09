package com.example.hp.qunyingzhuan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    private Button btnOut, btnHide, btnOpen, btnGone;

    private boolean isFlag = false;

    private int mHiddenViewMeasuredHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotification();

        findViewById(R.id.btn_to_scroll_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScrollActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_to_listview_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_to_slide_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SlideActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_to_dragview_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DragViewActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_to_image_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_to_surface_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SurfaceActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_to_bmob_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BmobActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this)
                        .toBundle());
            }
        });
        findViewById(R.id.btn_to_transition_a_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TransitionAActivity.class);
                startActivity(intent);
            }
        });

        btnOut = (Button)findViewById(R.id.btn_main_out);
        btnHide = (Button)findViewById(R.id.btn_main_hide);
        btnOpen = (Button)findViewById(R.id.btn_open_btn);
        btnGone = (Button)findViewById(R.id.btn_gone);

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlag){
                    endAnim();
                }else {
                    startAnim();
                }
            }
        });

        //像素密度
        float mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        mHiddenViewMeasuredHeight = (int)(mDensity *40 + 0.5);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClickToOpen();
            }
        });

    }

    /**
     * 属性动画
     */
    private void startAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnOut,"alpha", 1f, 0.5f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnHide, "translationX", 200F);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);

        //下面是设置回弹
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(animator, animator1);
        set.start();

        isFlag = true;
    }

    private void endAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnOut,"alpha", 0.5f, 1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnHide, "translationX", 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator, animator1);
        set.start();

        isFlag = false;
    }

    /**
     * 值变化动画
     *
     * @param tvTime
     */
    private void tvTimer(final TextView tvTime){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 6);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvTime.setText("$"+(int)animation.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(10000);
        valueAnimator.start();
    }

    public void btnClickToOpen(){
        if(btnGone.getVisibility() == View.GONE){
            animateOpen();
        }else {
            animateClose();
        }
    }

    private void animateOpen(){
        btnGone.setVisibility(View.VISIBLE);
        ValueAnimator animator = createAnimator(btnGone, 0, mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animateClose(){
        int height = btnGone.getHeight();
        ValueAnimator animator = createAnimator(btnGone, height, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                btnGone.setVisibility(View.GONE);
            }
        });

        animator.start();
    }

    private ValueAnimator createAnimator(final View view, int start, int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = value;
                //view.requestLayout();
                view.setLayoutParams(params);
            }
        });

        animator.setDuration(1000);

        return animator;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void addNotification(){
//        RemoteViews views = new RemoteViews(getPackageName(), R.layout.layout_notification);
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentText("吃呀二");
//        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        assert notificationManager != null;
//        notificationManager.notify(0, builder.build());
    }
}
