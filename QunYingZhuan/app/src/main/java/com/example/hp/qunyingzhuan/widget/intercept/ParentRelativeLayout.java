package com.example.hp.qunyingzhuan.widget.intercept;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @类名:PrarentRelativeLayout
 * @创建人:赵祖元
 * @创建时间：2018/8/25 23:07
 * @简述: 返回值是 True 即拦截
 */
public class ParentRelativeLayout extends RelativeLayout {
    private final String TAG = "EventIntercept";

    public ParentRelativeLayout(Context context) {
        super(context);
    }

    public ParentRelativeLayout(Context context, AttributeSet attrs) {
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
        Log.d(TAG,"Parent dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);    //执行事件
    }

    /**
     * 拦截触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"Parent onInterceptTouchEvent ");
        return super.onInterceptTouchEvent(ev);
        //return true;        实现拦截

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
                Log.d(TAG,"Parent down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"Parent move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"Parent up");
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.d(TAG,"Parent on click");
        super.setOnClickListener(l);
    }
}
