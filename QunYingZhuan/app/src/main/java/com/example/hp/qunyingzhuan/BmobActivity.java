package com.example.hp.qunyingzhuan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.qunyingzhuan.bean.Person;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class BmobActivity extends AppCompatActivity {

    private Person person;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须在添加内容之前调用
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //设置进入效果
        //滑动
        //getWindow().setEnterTransition(new Slide());
        //变色
        getWindow().setEnterTransition(new Fade());
        //爆炸
        //getWindow().setEnterTransition(new Explode());

        //设置退出效果
        getWindow().setExitTransition(new Explode());

        setContentView(R.layout.activity_bmob);

        Bmob.initialize(this, "f70625914862247a81c8cc5ed5d61633");

        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.rl_bmob);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qunying_image);
        new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                assert swatch != null;
                layout.setBackground(new ColorDrawable(swatch.getRgb()));

                TextView textView = (TextView)layout.findViewById(R.id.tv_bmob_rl);
                textView.setTextColor(swatch.getTitleTextColor());
            }
        });

        ViewOutlineProvider provider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate().translationZ(100f);
            }
        });

        findViewById(R.id.button4).setOutlineProvider(provider);
    }

    private void recordData(Person person){
        person.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Log.d("BmobActivity","Success back--->"+s);
                }else {
                    Log.d("BmobActivity","Failed back--->"+s);
                }
            }
        });
    }

    private Person querySingleData(String name){
        BmobQuery<Person> query = new BmobQuery<>();
        query.addWhereEqualTo("name",name);
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e == null){
                    person = list.get(0);
                    Log.d("BmobActivity", "Success back--->"+person.getName());
                }else {
                    person = null;
                    Log.d("BmobActivity","Failed back--->"+e);
                }
            }
        });

        return person;
    }

    public void onRecordClick(View view) {
        Person person = new Person();
        person.setName("赵祖元");
        person.setAge(19);
        person.setLike("爱好广泛");
        recordData(person);
    }

    public void onQueryData(View view) {
        querySingleData("赵祖元");
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}