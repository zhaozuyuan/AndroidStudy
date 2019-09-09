package com.example.hp.qunyingzhuan.widget.intercept;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * @类名:ChildButton
 * @创建人:赵祖元
 * @创建时间：2018/8/25 23:08
 * @简述:
 */
@SuppressLint("AppCompatCustomView")
public class ChildButton extends Button {
    private final String TAG = "EventIntercept";

    public ChildButton(Context context) {
        super(context);
    }

    public ChildButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"Child down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"Child move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"Child up");
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.d(TAG,"Child on click");
        super.setOnClickListener(l);
    }
}
