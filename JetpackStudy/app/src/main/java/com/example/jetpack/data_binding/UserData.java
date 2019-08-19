package com.example.jetpack.data_binding;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jetpack.BR;

/**
 * create by zuyuan on 2019/8/15
 * 会在BR类里为UserData生成一个ID常量 可用来区分当前bind是哪一个类
 */
public class UserData {

    private String nameText;

    private Drawable headDrawable;

    public final AgeObservable ageObservable = new AgeObservable();

    public final CheckedObservable checkedObservable = new CheckedObservable();

    public static final String DEFAULT_NAME = "小明";

    public String getNameText() {
        return nameText;
    }

    void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public Drawable getHeadDrawable() {
        return headDrawable;
    }

    void setHeadDrawable(Drawable headDrawable) {
        this.headDrawable = headDrawable;
    }

    /**
     * 数据观察者
     */
    public static class AgeObservable extends BaseObservable {
        private String age;

        /**
         * 这个注解能够在 BR类里面为age生成一个资源ID
         */
        @Bindable
        public String getAge() {
            return age;
        }

        void setAge(String age) {
            this.age = age;
            notifyPropertyChanged(BR.age);
        }
    }

    public static class CheckedObservable extends BaseObservable {
        private boolean checked;

        @Bindable
        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            if (this.checked != checked) {
                this.checked = checked;
                Log.d("TAG", "选中" + checked);
                notifyPropertyChanged(BR.checked);
            }
        }
    }
}
