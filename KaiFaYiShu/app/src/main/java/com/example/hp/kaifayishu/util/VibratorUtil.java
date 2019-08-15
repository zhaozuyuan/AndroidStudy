package com.example.hp.kaifayishu.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * @author:zuyuan
 * @date：2018/10/12
 * @note:
 */
public final class VibratorUtil {
    public static void vibrate(Context context, long time) {
        if (context == null) {
            throw new NullPointerException("context can't be null");
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(time);
    }
}
