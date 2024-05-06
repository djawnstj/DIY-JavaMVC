package com.djawnstj.mvcframework.bean;

import java.lang.reflect.InvocationTargetException;

public class BeanFactory {

    public Object getBean(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
