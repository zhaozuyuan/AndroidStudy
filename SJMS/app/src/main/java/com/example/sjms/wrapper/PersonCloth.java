package com.example.sjms.wrapper;

/**
 * 人的穿着的抽象
 */
public abstract class PersonCloth extends Person {

    private Person mPerson;

    public PersonCloth(Person person) {
        mPerson = person;
    }

    @Override
    public void dressed() {
        mPerson.dressed();
        System.out.println("穿一件上衣、穿一条裤子");
    }
}
