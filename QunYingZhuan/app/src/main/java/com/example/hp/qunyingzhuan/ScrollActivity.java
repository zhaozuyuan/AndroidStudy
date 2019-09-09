package com.example.hp.qunyingzhuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.qunyingzhuan.widget.MyListView;

public class ScrollActivity extends AppCompatActivity {
    private MyListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
    }
}
