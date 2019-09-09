package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @类名:DragViewGroup
 * @创建人:赵祖元
 * @创建时间：2018/9/8 14:11
 * @简述:
 */
public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mHelper ;    //滑动帮助类

    private View mMenuView,mMainView ;   //侧滑菜单对象、主页面对象

    private int mWidth ;    //侧滑菜单的宽度


    public DragViewGroup(@NonNull Context context) {
        super(context);
        initView();
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 获取到子View
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //实现子布局的填充
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    /**
     * 用来获取参数，例如宽度、高度
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //获取到侧滑菜单的宽度
        mWidth = mMenuView.getMeasuredWidth();
    }

    /**
     * 进行事件的拦截,
     * shouldInterceptTouchEvent()判断是否应该拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 触摸事件的处理，
     * 将触摸事件传递给ViewDragHelper
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 触发滑动
     */
    @Override
    public void computeScroll() {
        if(mHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void initView(){
        mHelper = ViewDragHelper.create(this,callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * 何时开始检测触摸事件
         *
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //如果当前触摸的child是mMainView时开始检测
            return mMainView == child;
        }

        /**
         * 处理水平滑动
         *
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            //从左侧滑动
            return left;
        }

        /**
         * 处理垂直滑动
         *
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            //返回0即不进行滑动
            return 0;
        }

        /**
         * 拖动结束后调用
         *
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            //手指抬起来缓慢移动到指定位置
            if(mMainView.getLeft() < 400){
                //关闭菜单,回弹
                //相当于Scroller的start方法
                mHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }else {
                //打开菜单
                mHelper.smoothSlideViewTo(mMainView,mWidth,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }
    };
}
