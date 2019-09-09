package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @类名:ThreeModelView
 * @创建人:赵祖元
 * @创建时间：2018/8/22 19:59
 * @简述: 三种模式分别实现尺寸变化
 */
public class ThreeModelView extends View {
    private final String TAG = "ThreeModelView";
    public ThreeModelView(Context context) {
        super(context);
    }

    public ThreeModelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(
                measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG,TAG+"---widthSpecSize"+specSize);

        if(specMode == MeasureSpec.EXACTLY){        //精确值模式
            result = specSize;
        }else {
            result = 200;   //默认200px

            if(specMode == MeasureSpec.AT_MOST){       //自适配模式
                result = Math.min(result,specSize);
            }
        }

        return result;
    }

    private int measureHeight(int widthMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG,TAG+"---widthSpecSize"+specSize);

        if(specMode == MeasureSpec.EXACTLY){        //精确值模式
            result = specSize;
        }else {
            result = 100;   //默认200px

            if(specMode == MeasureSpec.AT_MOST){       //自适配模式
                result = Math.min(result,specSize);
            }
        }

        return result;
    }


}
