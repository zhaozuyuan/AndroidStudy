package com.example.hp.kaifayishu.window;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.RelativeLayout;

/**
 * @author:zuyuan
 * @date：2018/10/31
 * @note:
 */
public class ARelativeLayout extends RelativeLayout {
    private VelocityTracker mVelocityTracker;

    private int[] mVelocitys = new int[5];

    private int mIndex = 0;

    private OnTouchListener listener = null;

    private int mLastX, mLastY;

    private boolean isFirst = true;

    private boolean intercept = false;

    public ARelativeLayout(Context context) {
        super(context);
        init();
    }

    public ARelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastX = (int)ev.getX();
            mLastY = (int)ev.getY();
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE ||
                ev.getAction() == MotionEvent.ACTION_UP && listener != null) {
            if (isFirst) {
                int dx = Math.abs((int) ev.getX() - mLastX);
                int dy = Math.abs((int) ev.getY() - mLastY);
                if (dx - dy >= 0) {
                    isFirst = false;
                    intercept = true;
                } else {
                    if (ev.getAction() == MotionEvent.ACTION_UP) {
                        isFirst = true;
                    }
                }
            }
            return intercept;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);
            mVelocitys[mIndex] = (int) mVelocityTracker.getXVelocity();
            if (mIndex == 4) {
                mIndex = 0;
            } else {
                mIndex += 1;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int averageValue = ( mVelocitys[0] + mVelocitys[1] + mVelocitys[2] + mVelocitys[3]
                    +mVelocitys[4]) /5;
            if (averageValue >= 1800) {
                listener.trigger();
            }
            mVelocityTracker.clear();
            intercept = false;
        }
        return true;
    }

    interface OnTouchListener {
        /**
         * 触发（结束动画）
         */
        void trigger();
    }

    public void addOnTouchListener(OnTouchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
