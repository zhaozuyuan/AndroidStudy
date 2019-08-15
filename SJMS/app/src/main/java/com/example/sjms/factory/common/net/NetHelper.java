package com.example.sjms.factory.common.net;

/**
 * create by zuyuan on 2019/7/19
 */
public class NetHelper implements INetHelper {

    private static NetHelper mHelper;

    private NetHelper() { }

    public synchronized static NetHelper getInstance() {
        if (mHelper == null) mHelper = new NetHelper();
        return mHelper;
    }

    @Override
    public void request(CallBack callBack) {
        callBack.onResult("");
    }


    public static class Bean { }
}
