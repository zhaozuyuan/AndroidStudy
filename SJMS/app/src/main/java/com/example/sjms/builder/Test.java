package com.example.sjms.builder;

/**
 * create by zuyuan on 2019/5/16
 */
public class Test {
    public void start() {
        XiaoMin xm = new XiaoMin.Builder(null)
                .setPeopleAge(10)
                .setPeopleName("xiaoMin")
                .setTarget("目标")
                .build();
        xm.startStudy();
        xm.stopStudy();
    }
}
