package com.example.hp.kaifayishu.util;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author:zuyuan
 * @date：2018/10/12
 * @note:
 */
public final class AnimationUtil {

    public static void scale(View tV, float s, int time) {
        ObjectAnimator a = ObjectAnimator.ofFloat(tV, "scaleX", 1.0f, s);
        ObjectAnimator b = ObjectAnimator.ofFloat(tV, "scaleY", 1.0f, s);
        a.setDuration(time);
        b.setDuration(time);
        a.start();
        b.start();
    }
}
