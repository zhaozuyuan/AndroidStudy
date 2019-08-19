package com.example.jetpack.data_binding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.jetpack.R;
import com.example.jetpack.data_binding.vm.OtherData;
import com.example.jetpack.data_binding.vm.OtherModel;
import com.example.jetpack.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    private DataBindingClickListener mListener = new DataBindingClickListener() {
        @Override
        public void onChanePeopleClick(View view) {
            mUser.setNameText("小红");
            mUser.ageObservable.setAge("12");

            mBinding.setUserData(mUser);
            mOtherModel.requestByNet();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        initUserData();
        mBinding.setUserData(mUser);
        mBinding.setDataClick(mListener);

        mOtherModel = ViewModelProviders.of(this).get(OtherModel.class);
        mOtherModel.getmData().observe(this, new Observer<OtherData>() {
            @Override
            public void onChanged(OtherData otherData) {
                Toast.makeText(DataBindingActivity.this, "更新数据", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
      未使用LiveData和ViewModel
     */

    ActivityDataBindingBinding mBinding;

    private UserData mUser;

    private void initUserData() {
        mUser = new UserData();
        mUser.setNameText("小明");
        mUser.setHeadDrawable(getDrawable(R.drawable.img_1));
    }


    @BindingAdapter(value = {"image", "other"}, requireAll = false)
    public static void loadCircleImage(ImageView view, Drawable image, String other) {
        Glide.with(view).asDrawable().load(image).into(view);
    }

    /*
     使用LiveData和ViewModel
     */

    private OtherModel mOtherModel;
}
