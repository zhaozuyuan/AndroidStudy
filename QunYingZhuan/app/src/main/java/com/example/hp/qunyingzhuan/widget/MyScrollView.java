package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * @类名:MyScrollView
 * @创建人:赵祖元
 * @创建时间：2018/8/23 14:53
 * @简述:
 */
public class MyScrollView extends ViewGroup {
    private final String TAG = "MyScrollView";

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.mScroller = new Scroller(context);
        initDisplayParams();
    }

    private Context context;

    private int mScreenHeight ; //屏幕高度(px)

    private int mLastY ;    //点击下的屏幕坐标

    private int mStart ;    //手指按下到现在的滑动距离

    private Scroller mScroller; //滑动对象

    private int mEnd;       //手指抬起到现在的滑动距离

    private long speed = 0;

    private long oneTime = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //设置该控件的总体高度
        MarginLayoutParams h = (MarginLayoutParams)getLayoutParams();
        h.height = mScreenHeight*getChildCount();
        setLayoutParams(h);

        for(int i = 0;i < getChildCount();i++){
            View child = getChildAt(i);
            if(child.getVisibility() != GONE){
                //修改top和bottom，让其能够按顺序排列
                child.layout(l,i*mScreenHeight,r,(i+1)*mScreenHeight);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();
        for(int i = 0;i < count;i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec); //传入的px值
        }
    }

    /**
     * 实现粘性滑动
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:   //按下,起点
                //oneTime = SystemClock.elapsedRealtime();
                Log.d(TAG,"ACTION_DOWN");
                mLastY = y;
                mStart = getScrollY();      //起点滑动的距离

                Log.d(TAG,"mLastY（点击坐标，一个屏幕的Y轴的坐标）---"+mLastY+"px");
                Log.d(TAG,"mStart（从一开始到现在滑动的距离）---"+getScrollY()+"px");
                break;

            case MotionEvent.ACTION_MOVE:    //手指滑动
//                long time = SystemClock.elapsedRealtime();
                Log.d(TAG,"ACTION_MOVE");
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation(); //中止动画
                }
                int dy = mLastY - y;    //滑动距离，点击坐标-滑动过程中坐标 = 滑动距离

//                speed = dy*100/(time - oneTime);
                //Log.d(TAG,"speed"+speed);
                if(getScrollY() < 0){   //一开始到现在滑动距离必须大于0
                    dy = 0;
                }
                if(getScrollY() > getHeight()-mScreenHeight){   //getHeight()得到的是布局总高度，到底不滑动
                    dy = 0;
                }
                Log.d(TAG,"ScrollHeight---"+getScrollY());
                scrollBy(0,dy);
                mLastY = y;
                //oneTime = SystemClock.elapsedRealtime();
                break;

            case MotionEvent.ACTION_UP:     //手指抬起
                Log.d(TAG,"ACTION_UP");
                mEnd = getScrollY();        //得到滑动的总体距离
                int dScrollY = mEnd - mStart;   //滑动距离

//                if(speed > 400){
//                    mScroller.startScroll(0,getScrollY(),0,getScrollY()+3*mScreenHeight);
//                }
                if(dScrollY > 0){
                    if(dScrollY < mScreenHeight /3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY);  //收回
                    }else {
                        //继续弹出
                        mScroller.startScroll(0,getScrollY(),0,mScreenHeight - dScrollY);
                    }
                }else {
                    if(-dScrollY < mScreenHeight/3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY);
                    }else {
                        mScroller.startScroll(0,getScrollY(),0,-mScreenHeight-dScrollY);
                    }
                }
                break;

            default:
                break;

        }

        postInvalidate();
        return true;
    }

    /**
     * 计算滚动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());   //形成连贯动画

            postInvalidate();   //刷新布局
        }
    }

    /**
     * 得到屏幕高度mScreenHeight(dp)
     */
    private void initDisplayParams(){
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        assert manager != null;
        manager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;         // 屏幕宽度（像素）
        int height = metrics.heightPixels;       // 屏幕高度（像素）
        float density = metrics.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metrics.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）

        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        mScreenHeight = (int) (height / density);// 屏幕高度(dp)

        mScreenHeight = height;     //这个才是px
        Log.d(TAG,"mScreenHeight---"+mScreenHeight+"px");
    }
}
