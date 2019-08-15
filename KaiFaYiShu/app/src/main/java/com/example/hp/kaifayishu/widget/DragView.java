package com.example.hp.kaifayishu.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hp.kaifayishu.util.TimeOut;
import com.example.hp.kaifayishu.util.VibratorUtil;

/**
 * @author:zuyuan
 * @date：2018/10/12
 * @note: 莫名其妙，view应该使用getRaw() 和 getRawY()，而viewGroup应该使用getX() 和 getY();
 */
public class DragView extends RelativeLayout {

    private boolean isMove  = false;

    private boolean isSelected = false;

    private TimeOut out ;

    private int mLastX, mLastY;

    private int rootHeight, rootWidth;

    private int width, height;

    private Button b1, b2, b3;

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        b1 = (Button) getChildAt(0);
        b2 = (Button) getChildAt(1);
        b3 = (Button) getChildAt(2);

        getChildAt(3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator a = ObjectAnimator.ofFloat(b1, "translationX",
                        200);
                Log.d("TAG", "onClick");
                a.start();
                getLayoutParams().width = 800;
                getLayoutParams().height = 1000;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int offsetX = x - mLastX;
            int offsetY = y - mLastY;

            int l = getLeft() + offsetX;
            int t = getTop() + offsetY;
            int r = getRight() + offsetX;
            int b = getBottom() + offsetY;

            layout(l, t, r, b);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            mLastX = x;
            mLastY = y;
            isMove = false;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                rootWidth = getRootView().getMeasuredWidth();
                rootHeight = getRootView().getMeasuredHeight();
                width = getMeasuredWidth();
                height = getMeasuredHeight();

                isSelected = false;
                out = new TimeOut();
                out.startTiming(500, new Handler.Callback(){
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (!isMove) {
                            VibratorUtil.vibrate(DragView.this.getContext(), 30);
                            onTouchEvent(ev);
                            isSelected = true;
                        }
                        return true;
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:
                if (out != null) {
                    out.cancel();
                    out = null;
                }
                isMove = false;
                break;
        }

        return isSelected;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
    }


}
