package com.example.hp.qunyingzhuan;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.hp.qunyingzhuan.adapter.MyListAdapter;
import com.example.hp.qunyingzhuan.widget.MyListView;

public class ListActivity extends AppCompatActivity {
    private MyListView listView;

    private MyListAdapter adapter;

    private float mFirstY = 0;

    private float mCurrentY = 0;

    private int direction = 0;

    private boolean mShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initListView();
    }

    @SuppressLint("PrivateResource")
    private void initListView(){
        listView = (MyListView)findViewById(R.id.lv_list);

        View headItem = getLayoutInflater().inflate(R.layout.item_list_head,null);
        headItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int)getResources().getDimension(R.dimen.abc_action_bar_default_height_material))); //默认标题栏高度
        listView.addHeaderView(headItem);

        adapter = new MyListAdapter(this.getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setCurrentItem(position - 1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if(mCurrentY - mFirstY > 2){
                        direction = 0;
                    }else if(mFirstY - mCurrentY > 2){
                        direction = 1;
                    }

                    if(direction == 1){
                        if(mShow){
                            //MD，不会做动画！！！
                        }
                    }
            }
        return true;
        }
    };

}
