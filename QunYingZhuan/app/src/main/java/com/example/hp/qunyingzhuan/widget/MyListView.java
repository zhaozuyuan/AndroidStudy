package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;

/**
 * @类名:MyListView
 * @创建人:赵祖元
 * @创建时间：2018/8/26 0:12
 * @简述:
 */
public class MyListView extends ListView {
    private int maxOverY = 140;

    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * @param deltaX
     * @param deltaY
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY 通过这个值的修改就可以实现弹性ListView了
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverY, isTouchEvent);
    }

    public void initView(){
        DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        Log.d("MyListView","DisplayMetrics.density---"+metrics.density);
        maxOverY = (int)(density*maxOverY);  //适配不同分辨率拿到px
    }
}
