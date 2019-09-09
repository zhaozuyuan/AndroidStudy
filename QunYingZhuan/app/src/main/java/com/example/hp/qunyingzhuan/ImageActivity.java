package com.example.hp.qunyingzhuan;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.os.IInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.hp.qunyingzhuan.util.ColorMatrixUtil;

public class ImageActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private ImageView imageView;

    private SeekBar seekBar1, seekBar2, seekBar3;

    private Bitmap bitmap;

    //默认值都是127
    //蓝->绿->黄---色调
    private float mHue = 0f;
    //饱和度
    private float mSaturation = 1f;
    //亮度
    private float mLum = 1f;

    final int MID_VALUE = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Debug.startMethodTracing();

        //加载布局的动画
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.cl_image_activity);
        ScaleAnimation a = new ScaleAnimation(0, 1, 0, 1);
        a.setDuration(1000);
        LayoutAnimationController controller = new LayoutAnimationController(a, 0.5f);
        //layout.setLayoutAnimation(controller);

        ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationX", -400);
        animator.setDuration(300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(layout, "translationX", 0);
        animator.setDuration(400);
        AnimatorSet set = new AnimatorSet();
        //set.playTogether(animator,animator2);
        set.play(animator).before(animator2);
        set.start();

        imageView = (ImageView)findViewById(R.id.imageView);
        seekBar1 = (SeekBar)findViewById(R.id.seekBar);
        seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar)findViewById(R.id.seekBar3);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qunying_image);
        imageView.setImageBitmap(bitmap);

        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBar:
                mHue = (progress - MID_VALUE)*1.0f/MID_VALUE*180;
                Log.d("ImageActivity",""+progress);
                break;
            case R.id.seekBar2:
                mSaturation = progress*1.0f/MID_VALUE;
                Log.d("ImageActivity",""+progress);
                break;
            case R.id.seekBar3:
                mLum = progress*1.0f/MID_VALUE;
                Log.d("ImageActivity",""+progress);
                break;
            default:
                break;
        }

        imageView.setImageBitmap(ColorMatrixUtil.handleImageEffect(bitmap,mHue, mSaturation, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void toDetailActivity(View view) {
        startActivity(new Intent(this,ImageMatrixActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.stopMethodTracing();
    }
}
