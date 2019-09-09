package com.example.hp.qunyingzhuan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @类名:FlashText
 * @创建人:赵祖元
 * @创建时间：2018/8/23 9:19
 * @简述:
 */
@SuppressLint("AppCompatCustomView")
public class FlashText extends TextView {
    public FlashText(Context context) {
        super(context);
    }

    public FlashText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    Paint mPaint;
    LinearGradient mLinearGradient;
    int mTranslate;
    int mViewWidth;

    Matrix mGradientMatrix;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if(mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0 ,
                        new int[]{
                        Color.BLUE,0Xffffffff,
                        Color.BLUE},
                        null, Shader.TileMode.CLAMP);

                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mGradientMatrix != null){
            mTranslate += mViewWidth/5;
            if(mTranslate > 2*mViewWidth){
                mTranslate = -mViewWidth;
            }

            mGradientMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
