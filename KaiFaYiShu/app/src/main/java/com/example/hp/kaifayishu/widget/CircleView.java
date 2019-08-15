package com.example.hp.kaifayishu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hp.kaifayishu.R;

/**
 * @author:zuyuan
 * @date：2018/10/18
 * @note:
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    //抗锯齿
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * @param attrs 就是传入的规则
     */
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs!= null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
            mColor = a.getColor(R.styleable.CircleView_circleColor, Color.RED);
            a.recycle();    //记住要回收
        }
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //考虑padding
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBoottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingBoottom - paddingTop;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
