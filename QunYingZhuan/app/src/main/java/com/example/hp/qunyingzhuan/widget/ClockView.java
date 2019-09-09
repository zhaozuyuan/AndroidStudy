package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @类名:ClockView
 * @创建人:赵祖元
 * @创建时间：2018/9/10 16:55
 * @简述:
 */
public class ClockView extends View {

    private Paint paintCircle, paintDegree, paintHour, paintMinute ;

    private int mWidth, mHeight ;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = (mWidth > mHeight ? mHeight:mWidth)/2 - 3;

        //画圆
        canvas.drawCircle(mWidth/2, mHeight/2, radius, paintCircle);

        paintCircle.setStrokeWidth(3);
        for(int i = 0;i < 24;i++){
            //区分0、6、12、18点
            if(i == 0||i == 6||i == 12||i == 18){
                paintDegree.setStrokeWidth(5);
                canvas.drawLine(mWidth/2, mHeight/ 2 - mWidth/2,
                        mWidth/2, mHeight/2 - mWidth/2 + 60,
                        paintDegree);

                paintDegree.setTextSize(25);
                //此处设置字体加粗，貌似所有的都加粗了
                Typeface font = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
                paintDegree.setTypeface(font);
                String degree = String.valueOf(i);
                canvas.drawText(degree,mWidth/2 - paintDegree.measureText(degree)/2,
                        mHeight/2 - mWidth/2 + 90,
                        paintDegree);
            }else {
                paintDegree.setStrokeWidth(3);
                canvas.drawLine(mWidth/2, mHeight/ 2 - mWidth/2,
                        mWidth/2, mHeight/2 - mWidth/2 + 30,
                        paintDegree);

                paintDegree.setTextSize(18);
                String degree = String.valueOf(i);
                canvas.drawText(degree,mWidth/2 - paintDegree.measureText(degree)/2,
                        mHeight/2 - mWidth/2 + 60,
                        paintDegree);
            }

            //每画一个时间旋转15度
            canvas.rotate(15, mWidth/2, mHeight/2);
        }

        //保存画布
        canvas.save();

        //时针和分针
        canvas.translate(mWidth/2, mHeight/2);
        canvas.drawLine(0, 0, 80, 80,paintHour);
        canvas.drawLine(0, 0, 60, 150, paintMinute);
        //合并图层
        canvas.restore();

        super.onDraw(canvas);
    }

    private void init(){
        paintCircle = new Paint();
        paintDegree = new Paint();
        paintHour = new Paint();
        paintMinute = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);

        paintHour.setStrokeWidth(14);
        paintMinute.setStrokeWidth(8);
    }
}
