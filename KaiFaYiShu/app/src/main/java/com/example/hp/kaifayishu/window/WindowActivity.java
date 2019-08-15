package com.example.hp.kaifayishu.window;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;

import com.example.hp.kaifayishu.R;

import java.util.logging.Logger;

public class WindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        ARelativeLayout layout = (ARelativeLayout)findViewById(R.id.layout_widow);
        layout.addOnTouchListener(new ARelativeLayout.OnTouchListener() {
            @Override
            public void trigger() {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.layout_right_in, R.anim.layout_right_out);
    }
}
