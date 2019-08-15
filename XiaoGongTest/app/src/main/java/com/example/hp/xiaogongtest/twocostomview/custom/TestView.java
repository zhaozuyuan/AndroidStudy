package com.example.hp.xiaogongtest.twocostomview.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @类名:testView
 * @创建人:赵祖元
 * @创建时间：2018/8/1 11:43
 * @简述:
 */
public class TestView extends View {
    private Paint mPaint;     //创建画笔

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);


        canvas.drawCircle(150,150,120,mPaint);

        canvas.drawRoundRect(100,300,500,500,40,40,mPaint);

        RectF rect = new RectF(1000,100,1400,500);   // 矩形区域

        for (int i=0; i<=10; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect,mPaint);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initPaint(){
        mPaint = new Paint();

        mPaint.setColor(Color.GRAY);      //画笔颜色

        mPaint.setStyle(Paint.Style.STROKE);  //填充

        mPaint.setStrokeWidth(20f);      //画笔宽度

    }
}
