package com.djawnstj.mvcframework.bean;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private final Map<String, Object> bean = new HashMap<>();

    public Map<String, Object> getBean(Set<Class<?>> classSet) {
        List<Constructor<?>> defaultConstructors = getDefaultConstructor(classSet);

        defaultConstructors.forEach(constructor -> {
            try {
                bean.put(constructor.getName(), constructor.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return bean;
    }

    private List<Constructor<?>> getDefaultConstructor(Set<Class<?>> classSet) {
        List<Constructor<?>> allDeclaredConstructors = getAllDeclaredConstructors(classSet);

        return allDeclaredConstructors.stream()
                .filter(constructor -> constructor.getParameters().length == 0)
                .collect(Collectors.toList());
    }

    private List<Constructor<?>> getAllDeclaredConstructors(Set<Class<?>> classSet) {
        List<Constructor<?>> allDeclaredConstructors = new ArrayList<>();

        classSet.forEach(clazz -> {
            allDeclaredConstructors.addAll(List.of(clazz.getDeclaredConstructors()));
        });

        return allDeclaredConstructors;
    }

}
