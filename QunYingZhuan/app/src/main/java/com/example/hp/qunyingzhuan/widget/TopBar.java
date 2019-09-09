package com.example.hp.qunyingzhuan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.qunyingzhuan.R;

/**
 * @类名:TopBar
 * @创建人:赵祖元
 * @创建时间：2018/8/23 10:22
 * @简述:
 */
public class TopBar extends LinearLayout {
    Context context;

    TypedArray ta;

    String mLeftText;
    int mLeftTextColor;
    Drawable mLeftBackground;

    String mRightText;
    int mRightTextColor;
    Drawable mRightBackground;

    String mTitleText;
    int mTitleTextColor;
    float mTitleTextSize;

    Button mLeftBtn;
    Button mRightBtn;
    TextView mTitleTv;

    LayoutParams mLeftParams ;
    LayoutParams mRightParams ;
    LayoutParams mTitleParams ;

    TopBarClickListener listener ;

    public static final int LR_GONE = 0 ;
    public static final int L_GONE = 1 ;
    public static final int R_GONE = 2 ;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        setOrientation(HORIZONTAL);
        initParam();
        initView();
    }

    private void initView(){
        mLeftBtn = new Button(context);
        mRightBtn = new Button(context);
        mTitleTv = new TextView(context);

        mTitleTv.setText(mTitleText);
        mTitleTv.setTextSize(mTitleTextSize);
        mTitleTv.setTextColor(mTitleTextColor);

        mLeftBtn.setText(mLeftText);
        mLeftBtn.setTextColor(mLeftTextColor);
        mLeftBtn.setBackground(mLeftBackground);

        mRightBtn.setText(mRightText);
        mRightBtn.setTextColor(mRightTextColor);
        mRightBtn.setBackground(mRightBackground);

        mTitleTv.setGravity(Gravity.CENTER_HORIZONTAL);
        mRightBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        mLeftBtn.setGravity(Gravity.CENTER_HORIZONTAL);

        mLeftParams = new LayoutParams(0, LinearLayout.LayoutParams.
                WRAP_CONTENT);
        mLeftParams.weight = 2;
        addView(mLeftBtn,mLeftParams);

        mTitleParams = new LayoutParams(0, LayoutParams.
                WRAP_CONTENT);
        mTitleParams.weight = 4;
        addView(mTitleTv,mTitleParams);

        mRightParams = new LayoutParams(0, LinearLayout.LayoutParams.
                WRAP_CONTENT);
        mRightParams.weight = 2;
        addView(mRightBtn,mRightParams);

        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.leftClick();
                }
                Log.d("TAG",TopBar.this.getChildAt(0).getX()+"----0");
                Log.d("TAG",TopBar.this.getChildAt(1).getX()+"----1");
                Log.d("TAG",TopBar.this.getChildAt(2).getX()+"----2");
            }
        });

        mRightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!= null) {
                    listener.rightClick();
                }
            }
        });

    }

    private void initParam(){
        mTitleText = ta.getString(R.styleable.TopBar_title);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor,0);
        mTitleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize,10);

        mLeftText = ta.getString(R.styleable.TopBar_leftText);
        mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor,0);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);

        mRightText = ta.getString(R.styleable.TopBar_rightText);
        mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor,0);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);

        ta.recycle();
    }

    public void setButtonVisable(int type){
        if(type == LR_GONE){
            mLeftBtn.setVisibility(GONE);
            mRightBtn.setVisibility(GONE);
        }else if(type == L_GONE){
            mLeftBtn.setVisibility(GONE);
        }else if(type == R_GONE){
            mRightBtn.setVisibility(GONE);
        }
    }

    public interface TopBarClickListener{
        void leftClick();
        void rightClick();
    }

    public void setTopBarOnClickListener(TopBarClickListener listener){
        this.listener = listener;
    }

}
