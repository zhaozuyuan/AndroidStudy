package com.example.hp.xiaogongtest.twocostomview.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * @类名:CircleImageView
 * @创建人:赵祖元
 * @创建时间：2018/7/31 21:47
 * @简述:
 *
 */

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView{
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs,int d) {
        super(context, attrs,d);
    }

    private Paint paint;

    private int radius;

    private float scale;    //缩放比例

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = Math.min(getMeasuredWidth(),getMeasuredHeight());
        Log.d("TAG",getMeasuredWidth()+"---"+getMeasuredHeight());
        radius = size / 3;

        //设置测量尺寸
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();

        Drawable drawable = getDrawable();

        if(drawable != null){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

            //初始化BitmapShader,传入bitmap对象
            @SuppressLint("DrawAllocation")
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP
                    ,Shader.TileMode.CLAMP);

            scale = (radius * 2.0f)/Math.min(bitmap.getHeight(),bitmap.getWidth());

            Matrix matrix = new Matrix();
            matrix.setScale(scale,scale);
            bitmapShader.setLocalMatrix(matrix);
            paint.setShader(bitmapShader);

            canvas.drawCircle(radius,radius,radius,paint);
        }
    }
}