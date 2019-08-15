package com.example.sjms.wrapper;

/**
 * 一个人的具体实现
 */
public class PersonImpl extends Person {

    /**
     * 穿衣服的具体实现
     */
    @Override
    public void dressed() {
        System.out.println("穿上内衣内裤");
    }
}
