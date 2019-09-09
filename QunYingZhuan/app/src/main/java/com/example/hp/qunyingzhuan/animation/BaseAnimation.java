package com.example.hp.qunyingzhuan.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.hp.qunyingzhuan.R;

/**
 * @类名:Transparency
 * @创建人:赵祖元
 * @创建时间：2018/9/19 23:00
 * @简述:
 */
public class BaseAnimation {

    private Context context = null;

    private View view;

    /**
     * 透明度动画
     */
    private void transparency(){
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        view.startAnimation(aa);
    }

    /**
     * 旋转动画
     */
    private void rotate(){
        //参数：起始角度，旋转点中心坐标
        RotateAnimation animation = new RotateAnimation(0, 360, 100, 100);
    }

    /**
     * 位移动画
     */
    private void translate(){
        TranslateAnimation animation = new TranslateAnimation(0, 10, 10, 10);
    }

    /**
     * 缩放动画
     *
     * 采用自身中心点缩放
     * 同理旋转动画可得
     */
    private void scale(){
        ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
    }

    /**
     * 设置动画监听
     */
    private void setCallBack(){
        Animation animation = new AlphaAnimation(0, 1);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*
      下面是Android3.0之后提出的新的动画框架
      可以实现更多的效果
      消耗资源较小
     */

    /**
     * 实现最简单的位移动画
     *
     * ObjectAnimator类操作的属性必须具有get、set方法
     * 若是没有，则自己创建static类，或者使用ValueAnimator
     */
    private void simple(){
        ObjectAnimator.ofFloat(view, "translationX", 300).start();

        WrapperView w = new WrapperView(view);
        ObjectAnimator.ofInt(w, "width", 500).start();
    }

    static class WrapperView{
        private View targetView;

        public WrapperView(View view){
            this.targetView = view;
        }

        /**
         * requestLayout() 是在View的位置发生改变时调用
         * view位置改变，只是重绘是不起效果的
         * requestLayout() 会重绘当前父布局，甚至更高层布局
         *
         * @param width
         */
        public void setWidth(int width){
            targetView.getLayoutParams().width = width;
            targetView.requestLayout();
        }

        /**
         * @return 实际占用的宽度
         */
        public int getWidth(){
            return targetView.getLayoutParams().width;
        }
    }

    /**
     * 复合动画 PropertyValuesHolder 实现多种效果
     *
     * 设置监听
     */
    private void translateAndScale(){
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationX", 300);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation000000000000) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //一般关注此事件
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //监听一个事件
            }
        });
        animator.start();
    }

    /**
     * 借助AnimatorSet实现复合动画的有序实现
     *
     * @param toNull
     */
    private void translateAndScale(int toNull){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 300f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animator1,animator2);
        set.start();

    }


    /**
     * 设置布局动画
     */
    private void layoutAnimation(){
        Activity activity = null;
        ConstraintLayout layout = (ConstraintLayout)activity.findViewById(R.id.cl_image_activity);
        ScaleAnimation a = new ScaleAnimation(0, 1, 0, 1);
        a.setDuration(1000);
        //第二个参数是设置view delay的时间
        LayoutAnimationController controller = new LayoutAnimationController(a, 0.5f);
        //当delay的时间不为0时，则可以设置子view的顺序
        //顺序
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //随机
        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);
        //反序
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
        //layout.setLayoutAnimation(controller);

        ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationX", -400);
        animator.setDuration(300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(layout, "translationX", 0);
        animator.setDuration(400);
        AnimatorSet set = new AnimatorSet();
        //set.playTogether(animator,animator2);
        set.play(animator).before(animator2);
        set.start();
    }

    /*
     Interpolator 插值器，设置动画的速度
     */


}
