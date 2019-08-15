package com.example.sjms.factory;

/**
 * create by zuyuan on 2019/7/19
 */
public class ModelFactory {

    public static <T> T createModel(Class<T> clazz) {
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
