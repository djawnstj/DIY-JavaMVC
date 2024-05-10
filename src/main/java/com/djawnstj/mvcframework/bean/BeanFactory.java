package com.djawnstj.mvcframework.bean;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class BeanFactory {

    private Set<Constructor<?>> bean = new HashSet<>();

    public Set<Constructor<?>> getBean(Set<Class<?>> classSet) {

        classSet.forEach(clazz -> {
            // Component 생성자들 다 가져옴
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

            // 생성자들 중에 기본 생성자들만 찾기
            for (Constructor<?> constructor : declaredConstructors) {
                // getParameters가 비어있는 것들
                if (constructor.getParameters().length == 0) {
                    bean.add(constructor);
                }
            }
        });

        return bean;
    }

}
