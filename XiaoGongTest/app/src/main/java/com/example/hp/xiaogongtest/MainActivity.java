package com.example.hp.xiaogongtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hp.xiaogongtest.fivesql.SQLActivity;
import com.example.hp.xiaogongtest.onecontrol.ViewPagerActivity;
import com.example.hp.xiaogongtest.twocostomview.custom.CustomActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.to_viewpager)
    Button toViewpager;
    @BindView(R.id.to_viewpager_image)
    Button toViewpagerImage;
    @BindView(R.id.to_custom)
    Button toCustom;
    @BindView(R.id.to_sql)
    Button toSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.to_viewpager, R.id.to_viewpager_image, R.id.to_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_viewpager:
                Intent intent = new Intent(this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.to_viewpager_image:
                //Intent intent1 = new Intent(this, ViewPager_ImageActivity.class);
                //startActivity(intent1);
                break;
            case R.id.to_custom:
                Intent intent2 = new Intent(this, CustomActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @OnClick(R.id.to_sql)
    public void onViewClicked() {
        Intent intent2 = new Intent(this, SQLActivity.class);
        startActivity(intent2);
    }
}
