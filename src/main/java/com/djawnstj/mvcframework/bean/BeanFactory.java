package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Component;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private final String packageName;
    private final Map<String, Object> beanMap = new HashMap<>();

    public void init() {
        ComponentScanner componentScanner = new ComponentScanner();
        Set<Class<?>> scanSet = componentScanner.scan(packageName, Component.class);
        initBeanMap(scanSet);
    }

    public BeanFactory(String packageName) {
        this.packageName = packageName;
    }

    private void initBeanMap(Set<Class<?>> classSet) {
        List<Constructor<?>> defaultConstructors = getDefaultConstructor(classSet);

        defaultConstructors.forEach(constructor -> {
            try {
                beanMap.put(constructor.getName(), constructor.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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

    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz.getName());
    }

}
