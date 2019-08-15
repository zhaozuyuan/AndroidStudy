package com.example.hp.kaifayishu.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.kaifayishu.R;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

    }

    public void onClick(View view) {
        final TextView textView = (TextView)findViewById(R.id.tv_progress);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.rl_progress);
        int width = layout.getMeasuredWidth();

        ValueAnimator animator = ValueAnimator.ofInt(1, width);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.getLayoutParams().width = (Integer) animation.getAnimatedValue();
                textView.requestLayout();
            }
        });
        animator.start();
    }
}
