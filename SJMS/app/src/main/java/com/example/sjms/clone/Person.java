package com.example.sjms.clone;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zuyuan on 2019/7/18
 * 原型模式 实现深拷贝
 * Cloneable 标识接口 代表当前类可用于拷贝数据
 */
public class Person implements Cloneable {
    String name;
    String like;
    int age;

    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //字符串对象本身就不可变，所以这里可以直接进行浅拷贝赋值（引用）
        try {

            Person person = (Person) super.clone();
    //        person.name = name;
    //        person.like = name;
    //        person.age = age;
            person.list = (ArrayList<Integer>) list.clone();
            return person;
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] s) {
        String a = " 小 明";
        String b = a;
        //删除头尾空白
        a.trim();

        System.out.println(a);
        System.out.println(b);

        Person p1 = new Person();
        p1.name = "111";
        Person p2= p1;
        Person p3 = new Person();
        p3.name = "333";
        p1 = p3;

        System.out.println(p1.name);
        System.out.println(p2.name);
    }
}
