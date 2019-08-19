package com.example.jetpack.data_binding.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * create by zuyuan on 2019/8/19
 */
public class OtherModel extends ViewModel {
    private final OtherData mOtherData = new OtherData();

    private MutableLiveData<OtherData> mData = new MutableLiveData<>();

    public LiveData<OtherData> getmData() {
        return mData;
    }

    public void requestByNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mData.postValue(mOtherData);
            }
        }).start();
    }
}
