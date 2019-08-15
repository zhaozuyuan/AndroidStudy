package com.example.sjms.builder;

import android.content.Context;

/**
 * create by zuyuan on 2019/5/16
 */
public class XiaoMin {

    private Bean mBean;

    private String mTarget;

    private XiaoMin() {}

    public void startStudy() {
    }

    public void stopStudy() {
    }


    public static class Builder {

        private Context context;

        private Bean bean;

        private String target;

        public Builder(Context context) {
            this.context = context;
            bean = new Bean();
        }

        public Builder(Context context, Bean bean) {
            this.bean = bean;
        }

        public Builder setPeopleName(String name) {
            bean.setName(name);
            return this;
        }

        public Builder setPeopleAge(int age) {
            bean.setAge(age);
            return this;
        }

        public Builder setTarget(String target) {
            if (target.isEmpty()) throw new RuntimeException("target not be null");
            this.target = target;
            return this;
        }

        public XiaoMin build() {
            XiaoMin xm = new XiaoMin();
            xm.mBean = bean;
            xm.mTarget = target;
            return xm;
        }
    }
}
