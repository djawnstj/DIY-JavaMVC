package com.djawnstj.mvcframework.bean;

import java.lang.reflect.InvocationTargetException;

public class BeanFactory {

    private Object classInstance;

    public Object getBean(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        if (classInstance == null) {
            classInstance = clazz.getDeclaredConstructor().newInstance();
        }

        return classInstance;
    }

}
