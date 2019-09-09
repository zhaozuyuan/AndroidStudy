package com.example.hp.qunyingzhuan.bean;

import cn.bmob.v3.BmobObject;

/**
 * @类名:Person
 * @创建人:赵祖元
 * @创建时间：2018/9/22 13:40
 * @简述:
 */
public class Person extends BmobObject{
    private String name;
    private int age;
    private String like;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
