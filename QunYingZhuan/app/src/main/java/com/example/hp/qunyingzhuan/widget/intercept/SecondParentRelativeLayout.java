package com.example.hp.qunyingzhuan.widget.intercept;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @类名:SecondParentRelative
 * @创建人:赵祖元
 * @创建时间：2018/8/25 23:08
 * @简述:
 */
public class SecondParentRelativeLayout extends RelativeLayout {
    private final String TAG = "EventIntercept";

    public SecondParentRelativeLayout(Context context) {
        super(context);
    }

    public SecondParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 发送触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"Second parent dispatchTouchEvent");
//        return super.dispatchTouchEvent(ev);
        return false;   //不进行事件处理，执行默认将事件处理交给上级
    }

    /**
     * 拦截触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"Second parent onInterceptTouchEvent");
//        return super.onInterceptTouchEvent(ev);
        return true;   //拦截事件
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"Second parent down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"Second parent move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"Second parent up");
                break;
            default:
                break;
        }

        return false;       //给上级处理（当然在可以执行事件条件情况下，依然会执行其事件）
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.d(TAG,"Second parent on click");
        super.setOnClickListener(l);
    }
}
