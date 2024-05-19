package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Component;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private final String packageName;
    private final ComponentScanner componentScanner = new ComponentScanner();
    private final HashMap<String, Object> beanMap = new HashMap<>();
    private final HashMap<String, Constructor<?>> constructorBeanMap = new HashMap<>();

    public void init() {
        Set<Class<?>> scanSet = componentScanner.scan(packageName, Component.class);

        initBeanMap(scanSet);

        initConstructorBeanMap(scanSet);
    }

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    private void initBeanMap(final Set<Class<?>> classSet) {
        List<Constructor<?>> defaultConstructors = getDefaultConstructor(classSet);

        defaultConstructors.forEach(constructor -> {
            try {
                beanMap.put(constructor.getName(), constructor.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initConstructorBeanMap(final Set<Class<?>> classSet) {
        List<Constructor<?>> constructors = checkConstructorsBeforeGetAutoWired(classSet);
        List<Constructor<?>> autoWiredConstructors = getAutoWiredConstructors(constructors);

        Constructor<?> autoWiredConstructor = getAutoWiredConstructor(autoWiredConstructors);
        autoWiredConstructor.setAccessible(true);
        constructorBeanMap.put(autoWiredConstructor.getName(), autoWiredConstructor);
    }

    private List<Constructor<?>> checkConstructorsBeforeGetAutoWired(final Set<Class<?>> classSet) {
        List<Constructor<?>> declaredConstructors = new ArrayList<>();

        classSet.forEach(clazz -> {
            if (constructorBeanMap.containsKey(clazz.getName())) {
                return;
            }

            if (clazz.getDeclaredConstructors().length == 1) {
                constructorBeanMap.put(clazz.getName(), clazz.getDeclaredConstructors()[0]);
                return;
            }

            declaredConstructors.addAll(List.of(clazz.getDeclaredConstructors()));

        });

        return declaredConstructors;
    }

    private List<Constructor<?>> getAutoWiredConstructors(final List<Constructor<?>> list) {
        List<Constructor<?>> autowiredConstructors = new ArrayList<>();

        list.forEach(clazz -> {
            if (clazz.isAnnotationPresent(AutoWired.class)) {
                autowiredConstructors.add(clazz);
            }
        });

        return autowiredConstructors;
    }

    private Constructor<?> getAutoWiredConstructor(List<Constructor<?>> autoWiredConstructors) {
        if (autoWiredConstructors.size() > 1) {
            throw new RuntimeException();
        }

        return autoWiredConstructors.get(0);
    }

    private List<Constructor<?>> getDefaultConstructor(final Set<Class<?>> classSet) {
        List<Constructor<?>> allDeclaredConstructors = getAllDeclaredConstructors(classSet);

        return allDeclaredConstructors.stream()
                .filter(constructor -> constructor.getParameters().length == 0)
                .collect(Collectors.toList());
    }

    private List<Constructor<?>> getAllDeclaredConstructors(final Set<Class<?>> classSet) {
        List<Constructor<?>> allDeclaredConstructors = new ArrayList<>();

        classSet.forEach(clazz -> {
            allDeclaredConstructors.addAll(List.of(clazz.getDeclaredConstructors()));
        });

        return allDeclaredConstructors;
    }

    public Object getBean(final Class<?> clazz) {
        return beanMap.get(clazz.getName());
    }

    public HashMap<String, Constructor<?>> getConstructorBeanMap() {
        return constructorBeanMap;
    }

}
