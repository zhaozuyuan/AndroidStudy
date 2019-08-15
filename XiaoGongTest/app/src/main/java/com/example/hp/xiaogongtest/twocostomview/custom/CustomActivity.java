package com.example.hp.xiaogongtest.twocostomview.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.example.hp.xiaogongtest.R;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


        getDisplayMetrics();
    }

    private void getDisplayMetrics(){
        Display display =  getWindowManager().getDefaultDisplay();  //获得显示器

        DisplayMetrics metrics  = new DisplayMetrics();     //显示器矩阵

        display.getMetrics(metrics);        //得到显示器矩阵

        Log.d("Display","宽度（有多少个像素点）:"+metrics.widthPixels+"px");
        Log.d("Display","x轴的dpi(每英寸多少个像素点):"+metrics.xdpi+"dpi");
    }
}
