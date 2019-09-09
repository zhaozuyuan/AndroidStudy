package com.example.hp.qunyingzhuan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.qunyingzhuan.R;
import com.example.hp.qunyingzhuan.widget.MyListView;

import java.util.zip.Inflater;

/**
 * @类名:MyListAdapter
 * @创建人:赵祖元
 * @创建时间：2018/8/26 0:48
 * @简述:
 */
public class MyListAdapter extends BaseAdapter {
    private Context context;

    private int mCurrentItem = 100;

    public MyListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(mCurrentItem == position){
            return addNormalView();
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list,null);
        return convertView;
    }


    private View addNormalView(){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.beauty);
        //layout.addView(iv,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
         //       ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(context);
        tv.setText("Beautiful Girl");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(16);
        layout.addView(tv,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.setGravity(Gravity.CENTER);

        return layout;
    }

    public void setCurrentItem(int onClickPosition){
        this.mCurrentItem = onClickPosition;
    }
}
