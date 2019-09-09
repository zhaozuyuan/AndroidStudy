package com.example.jetpack.data_binding.vm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jetpack.MainActivity;

/**
 * create by zuyuan on 2019/8/26
 */
public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FrameLayout layout = new FrameLayout(getContext());

        //Activity是一个Context对象
        //Fragment不是Context对象，因此提供了getContext()方法获取该对象
        Context context = getContext();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

        return layout;
    }
}
