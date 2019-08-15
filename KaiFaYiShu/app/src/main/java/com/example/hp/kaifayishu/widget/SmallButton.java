package com.example.hp.kaifayishu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;


/**
 * @author:zuyuan
 * @date：2018/10/12
 * @note:
 */
@SuppressLint("AppCompatCustomView")
public class SmallButton extends Button {

    private Button btn1, btn2, btn3;

    public SmallButton(Context context) {
        super(context);
    }

    public SmallButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
