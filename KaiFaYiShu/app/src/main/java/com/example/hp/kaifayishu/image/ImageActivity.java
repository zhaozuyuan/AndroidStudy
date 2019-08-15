package com.example.hp.kaifayishu.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.example.hp.kaifayishu.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须加在之前
        //请求窗口特征 这个在Activity 的情况下有效
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);

        initImageView();
    }

    private void initImageView() {
        ImageView imageView = (ImageView)findViewById(R.id.iv_image);
        ImageLoader loader = ImageLoader.build(this.getApplicationContext());
        String url = "http://b-ssl.duitang.com/uploads/item/201503/07/20150307210848_JQhzE.jpeg";
        //loader.bindBitmapFromHttp(imageView, url);
        loader.bindBitmap(url, imageView,
                imageView.getLayoutParams().width, imageView.getLayoutParams().height);
    }
}
