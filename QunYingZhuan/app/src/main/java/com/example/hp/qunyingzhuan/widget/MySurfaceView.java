package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @类名:MySurfaceView
 * @创建人:赵祖元
 * @创建时间：2018/9/19 18:01
 * @简述:
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //view持有者，进行生命周期管理
    private SurfaceHolder holder;

    //画布
    private Canvas canvas;

    //子线程标记位
    private boolean isDrawing;

    private Path path;

    private Paint paint;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        path = new Path();

        paint = new Paint();
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#000000"));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        while (isDrawing){
            draw();
        }

        //此处设置了线程休眠，以节约资源
        long end = System.currentTimeMillis();
        if(end - start < 100){
            try {
                Thread.sleep(100 - (end - start));
            }catch (InterruptedException e){

            }
        }
    }

    private void draw(){
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(path,paint);
        }catch (Exception e){
        }finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
