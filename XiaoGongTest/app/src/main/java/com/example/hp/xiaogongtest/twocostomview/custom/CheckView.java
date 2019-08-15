package com.example.hp.xiaogongtest.twocostomview.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.hp.xiaogongtest.R;

/**
 * @类名:CheckView
 * @创建人:赵祖元
 * @创建时间：2018/8/4 0:36
 * @简述:
 */
public class CheckView extends View {
    public CheckView(Context context) {
        super(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();

    }

    private static final int ANIM_NULL = 0;     //动画状态-没有
    private static final int ANIM_CHECK = 1;    //动画状态-开启
    private static final int ANIM_UNCHECK = 2;  //动画状态-结束

    private Context context;

    private int mWidth,mHeight;     //宽高

    private Handler handler;

    private Paint paint;       //画笔

    private Bitmap bitmap;

    private int animCurrentPage = -1;   //当前页码
    private int animMaxPage = 13;       //总页数
    private int animDuration = 500;     //动画时间
    private int animState = ANIM_NULL;  //动画状态

    private boolean isCheck = false;

    @SuppressLint("HandlerLeak")
    private void init(){
        paint = new Paint();
        paint.setColor(0xffFF5317);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mostbeautful);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(animCurrentPage < animMaxPage && animCurrentPage >= 0){
                    invalidate();   //终止

                    if(animState == ANIM_NULL)
                        return;
                    if(animState == ANIM_CHECK){
                        animCurrentPage++;
                    }else if(animState == ANIM_UNCHECK){
                        animCurrentPage--;
                    }

                    //发送空消息并延迟
                    this.sendEmptyMessageDelayed(0,animDuration / animMaxPage);
                    Log.e("TAG","Count="+animCurrentPage);
                }else {
                    animCurrentPage = -1;
                }

                invalidate();
                animState = ANIM_NULL;
            }
        };
    }

    /**
     * 确定大小
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //移动坐标系到画布中央
        canvas.translate(mWidth/2,mHeight/2);

        //绘制背景图片
        canvas.drawCircle(0,0,240,paint);

        int sideLength = bitmap.getHeight();

        //得到图像选区和实际测绘位置
        @SuppressLint("DrawAllocation")
        Rect src = new Rect(sideLength*animCurrentPage,0
                ,sideLength*(animCurrentPage + 1 ),sideLength);

        Rect dst = new Rect(-200,-200,200,200);

        //绘制
        canvas.drawBitmap(bitmap,src,dst,null);
    }

    /**
     * 选择
     */
    public void check(){
        if(animState != ANIM_NULL||isCheck)
            return;

        animState = ANIM_CHECK;
        animCurrentPage = 0;
        handler.sendEmptyMessageDelayed(0,animDuration/animMaxPage);

    }

    /**
     * 取消选择
     */
    public void unCheck(){
        if(animState != ANIM_NULL||(!isCheck))
            return;

        animState = ANIM_UNCHECK;
        animCurrentPage = animMaxPage - 1;
        handler.sendEmptyMessageDelayed(0,animDuration/animMaxPage);
        isCheck = false;
    }

    /**
     * 设置动画时常
     *
     * @param animDuration
     */
    public void setAnimDuration(int animDuration){
        if(animDuration <= 0)
            return;

        this.animDuration = animDuration;
    }

    /**
     * 设置背景圆形颜色
     *
     * @param color
     */
    public void setBackgroundColor(int color){
        paint.setColor(color);
    }
}
