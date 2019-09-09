package com.example.hp.qunyingzhuan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

public class TransitionBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int flag = getIntent().getIntExtra("flag",0);
        switch (flag){
            case 1:
                getWindow().setEnterTransition(new Explode());
                break;
            case 2:
                getWindow().setEnterTransition(new Slide());
                break;
            case 3:
                getWindow().setEnterTransition(new Fade());
                break;
            case 4:
                break;
        }
        setContentView(R.layout.activity_transition_b);
    }
}
