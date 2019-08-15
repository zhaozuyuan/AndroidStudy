package com.example.hp.kaifayishu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.hp.kaifayishu.util.AnimationUtil;
import com.example.hp.kaifayishu.util.VibratorUtil;

/**
 * @author:zuyuan
 * @date：2018/10/11
 * @note:
 */
@SuppressLint("AppCompatCustomView")
public class DragButton extends Button {

    private int mLastX = 0, mLastY = 0;

    public boolean isSelected = false;

    public DragButton(Context context) {
        super(context);
        init(context);
    }

    public DragButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG", "LongOnClick");
                VibratorUtil.vibrate(context, 50);
                AnimationUtil.scale(v, 1.2f, 200);
                isSelected = true;
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isSelected) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
            }

            return super.onTouchEvent(event);
        } else {
            int x = (int) event.getRawX() ;//手指坐标
            int y = (int) event.getRawY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    Log.d("TAG", "MOVE");
                    int deltaX = x - mLastX;
                    int deltaY = y - mLastY;

                    Log.d("TAG", "getLeft--->" + getLeft());
                    Log.d("TAG", "getRight--->" + getRight());
                    Log.d("TAG", "getTop--->" + getTop());
                    Log.d("TAG", "getBottom--->" + getBottom());
                    Log.d("TAG", "eventRawX--->" + (int) event.getRawX());
                    Log.d("TAG", "eventRawY--->" + (int) event.getRawY());
                    Log.d("TAG", "getX--->" + (int) getX());
                    Log.d("TAG", "getY--->" + (int) getY());
                    Log.d("TAG", "getRootHeight--->" + getRootView().getMeasuredHeight());

                    if (getLeft() + deltaX > 0 && getTop() + deltaY > 0 &&
                            getRight() + deltaX < getRootView().getWidth() &&
                            getBottom() + deltaY < getRootView().getMeasuredHeight() ) {
                        layout(getLeft() + deltaX, getTop() + deltaY,
                                getRight() + deltaX, getBottom() + deltaY);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    isSelected = false;
                    AnimationUtil.scale(this, 1.0f, 200);
                    break;

            }

            mLastX = x;
            mLastY = y;
            return true;
        }
    }
}
