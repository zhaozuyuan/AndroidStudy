package com.example.jetpack.data_binding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * create by zuyuan on 2019/8/19
 */
@BindingMethods({
        @BindingMethod(type = ImageView.class,
                attribute = "circle_image",
                method = "setCircleImage"),
})
@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCircleImage(Drawable drawable) {
        Glide.with(this)
                .asDrawable()
                .apply(new RequestOptions().transform(new CircleCrop()))
                .load(drawable)
                .into(this);
    }
}
