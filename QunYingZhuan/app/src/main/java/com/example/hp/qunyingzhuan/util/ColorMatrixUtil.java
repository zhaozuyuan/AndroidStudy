package com.example.hp.qunyingzhuan.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * @类名:ColorMatrix
 * @创建人:赵祖元
 * @创建时间：2018/9/11 23:03
 * @简述:
 */
public class ColorMatrixUtil {
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        //设置颜色的色调
        ColorMatrix hueMatrix = new ColorMatrix();
        //第一个参数，系统分别使用0、1、2来代表Red、Green、Blue三种颜色的处理；而第二个参数，就是需要处理的值
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        //设置颜色的饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        //saturation参数即代表设置颜色的饱和度的值，当饱和度为0时，图像就变成灰度图像了
        saturationMatrix.setSaturation(saturation);

        //设置颜色的亮度
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        //将矩阵的作用效果混合，从而叠加处理效果
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        /**
         * 设置好处理的颜色矩阵后，通过使用Paint类的setColorFilter()方法，将通过imageMatrix构造的
         * ColorMatrixColorFilter对象传递进去，并使用这个画笔来绘制原来的图像，从而将颜色矩阵作用到原图中
         */
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));

        /**
         * Android系统也不允许直接修改原图，类似Photoshop中的锁定，必须通过原图创建一个同样大小的Bitmap，并将
         * 原图绘制到该Bitmap中，以一个副本的形式来修改图像。
         */
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bm, 0, 0 ,paint);
        return bitmap;
    }
}
