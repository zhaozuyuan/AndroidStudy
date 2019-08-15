package com.example.sjms.factory;

import com.example.sjms.factory.common.net.INetHelper;

/**
 * create by zuyuan on 2019/7/19
 */
public abstract class BaseModel {
    protected INetHelper mNetHelper;

    public void BaseModel() {
        mNetHelper = null;
    }

    abstract void requestDataByNet(String url);
    abstract void requestDataBySql(String url);
}
