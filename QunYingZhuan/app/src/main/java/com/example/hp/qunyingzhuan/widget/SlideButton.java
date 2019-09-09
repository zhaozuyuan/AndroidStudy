package com.example.hp.qunyingzhuan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;

/**
 * @类名:ScorllButton
 * @创建人:赵祖元
 * @创建时间：2018/8/26 10:13
 * @简述:
 */
@SuppressLint("AppCompatCustomView")
public class SlideButton extends Button {
    private final String TAG = "SlideButton";

    private Scroller mScroller;

    private int lastX = 0;  //按下的坐标

    private int lastY = 0;  //按下的坐标

    public SlideButton(Context context) {
        super(context);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int)(event.getX());
        int rawY = (int)(event.getY());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //初始点坐标
                lastX = rawX;
                lastY = rawY;

                Log.d(TAG,"初始点"+lastX+","+lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;

                //第一种方法
                //layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY); //？？？

                //第二种方法
                //offsetLeftAndRight(offsetX);
                //offsetTopAndBottom(offsetY);

                //第三种方法，实现不了！！！
                //RelativeLayout.MarginLayoutParams params =(RelativeLayout.MarginLayoutParams)getLayoutParams();
                //params.leftMargin = getLeft() + offsetX;
                //params.rightMargin = getTop() + offsetY;
                //setLayoutParams(params);

                //lastX = rawX;
                //lastY = rawY;

                break;
            case MotionEvent.ACTION_UP:
                //第四种方法

                View p = (View) getParent();

                //此处应该使用父布局的初始位置
                mScroller.startScroll(p.getScrollX(),p.getScrollY(),-rawX,-rawY,500);
                //移动的盖板，那么是负移动
                invalidate();   //通知重绘

                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.d(TAG,"onClick");
        super.setOnClickListener(l);
    }


    /**
     * 配合Scroller使用的方法
     * 计算滚动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        //判断Scroller是否执行完毕，true代表未执行完毕
        if(mScroller.computeScrollOffset()){
            View parent = (View)getParent();

            //该方法是移动的子控件
            //实现获得当前滑动的坐标
            //这里滑动有一些问题，所以加了50做出调整
            parent.scrollTo(mScroller.getCurrX() + 50,mScroller.getCurrY() + 50);

            //重绘
            invalidate();
        }
    }
}
