package com.example.hp.qunyingzhuan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.hp.qunyingzhuan.R;

/**
 * @类名:SctratchView
 * @创建人:赵祖元
 * @创建时间：2018/9/19 17:30
 * @简述:
 */
@SuppressLint("AppCompatCustomView")
public class ScratchView extends View {

    private Bitmap mBgBitmap, mFgBitmap;
    private Paint paint;
    private Canvas canvas;
    private Path path;

    public ScratchView(Context context) {
        super(context);
        init();
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAlpha(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.ROUND);

        path = new Path();

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qunying_image);
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(mFgBitmap);
        canvas.drawColor(Color.GRAY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
        }
        canvas.drawPath(path, paint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawBitmap(mFgBitmap, 0, 0, null);
    }
}
