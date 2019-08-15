package com.example.hp.kaifayishu.util;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author:zuyuan
 * @date：2018/10/10
 * @note:
 */
public final class MoveViewUtil {
    public static void moveToRight(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",
                view.getWidth());
        animator.setDuration(1000);
        animator.start();
    }
}
