package com.example.hp.qunyingzhuan.util;

import android.content.Context;

/**
 * @类名:DisplayUtil
 * @创建人:赵祖元
 * @创建时间：2018/9/10 10:50
 * @简述: dp、sp 转化为 px 的工具类
 *
 * dpi: 每英寸的像素点
 * px: 像素点，一般代码里面设置的都是多少个px(手机1080x1920指的就是px)
 * dp: 用于手机适配的，不同的手机的dp转化为px的比例是不同的
 * sp: 类似于dp，一般用于文本
 *
 * (density=160)
 *
 * drawable-ldpi：  屏幕密度为120的手机设备
 * drawable-mdpi：  屏幕密度为160的手机设备（此为baseline，其他均以此为基准，在此设备上，1dp = 1px）
 * drawable-hdpi：  屏幕密度为240的手机设备
 * drawable-xhdpi： 屏幕密度为320的手机设备
 * drawable-xxhdpi：屏幕密度为480的手机设备
 *
 * 例如我们在density=160的屏幕中将控件移动到density=320的屏幕时，控件的dp转化就应该发生变化，否则会变小
 */
public class DisplayUtil {

    /**
     * 将px值转化为dp值
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxTodp(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

    /**
     * 将dp值转化为px值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpTopx(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);
    }

}
