package com.example.sjms.factory;

import com.example.sjms.factory.common.net.INetHelper;
import com.example.sjms.factory.common.net.NetHelper;

/**
 * create by zuyuan on 2019/7/19
 */
public class GroupModel extends BaseModel {
    @Override
    public void BaseModel() {
        mNetHelper = NetHelper.getInstance();
    }

    @Override
    public void requestDataByNet(String url) {
        mNetHelper.request(result -> { });
    }

    @Override
    public void requestDataBySql(String url) {

    }
}
