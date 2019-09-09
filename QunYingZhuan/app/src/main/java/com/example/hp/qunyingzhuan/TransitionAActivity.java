package com.example.hp.qunyingzhuan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class TransitionAActivity extends AppCompatActivity {

    private Intent intent;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_a);

        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
//        toolbar.setLogo(R.drawable.ic_small);
//        toolbar.setTitle("主标题");
//        toolbar.setSubtitle("副标题");
//        setSupportActionBar(toolbar);
    }

    public void explode(View view) {
        intent = new Intent(this, TransitionBActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void slide(View view) {
        intent = new Intent(this, TransitionBActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void fade(View view) {
        intent = new Intent(this, TransitionBActivity.class);
        intent.putExtra("flag", 3);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void share(View view) {
        View fab = findViewById(R.id.btn_transition_a_share);
        intent = new Intent(this, TransitionBActivity.class);
        intent.putExtra("flag", 4);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view,
                "share").toBundle());

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(view,
                "share"), Pair.create(fab, "fab")).toBundle());
    }

    public void openAnimation(View view) {
        final ImageView imageView = (ImageView)findViewById(R.id.iv_oval);
        Animator animator = ViewAnimationUtils.createCircularReveal(imageView, imageView.getWidth()/2,
                imageView.getHeight()/2, imageView.getWidth(), 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }
}
