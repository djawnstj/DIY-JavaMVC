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

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    public void init() {
        // ComponentScanner에 의존하고 있다... test mocking
        Set<Class<?>> scanSet = componentScanner.scan(packageName, Component.class);

        createBeanMap(scanSet);

    }

    private void createBeanMap(final Set<Class<?>> classSet) {
        classSet.stream()
                .filter(this::isNotContainInBeanMap)
                .forEach(this::createBean);
    }

    private boolean isNotContainInBeanMap(final Class<?> clazz) {
        return !beanMap.containsKey(clazz.getName());
    }

    private void createBean(final Class<?> clazz) {
        Constructor<?> constructor = getConstructor(clazz);
        Object[] parameters = getParametersThroughBeanMap(constructor);

        try {
            beanMap.put(constructor.getName(), constructor.newInstance(parameters));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> getConstructor(final Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 1) { return constructors[0]; }

        return getDefaultOrAutoWiredConstructor(clazz);
    }

    private Constructor<?> getDefaultOrAutoWiredConstructor(final Class<?> clazz) {
        List<Constructor<?>> autowiredConstructors = findAutoWiredConstructors(clazz);

        if (autowiredConstructors.isEmpty()) {
            autowiredConstructors.add(getDefaultConstructor(clazz));
        }

        validateSingleAutowiredConstructor(autowiredConstructors);

        return autowiredConstructors.get(0);
    }

    private List<Constructor<?>> findAutoWiredConstructors(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(this::isAutoWiredAnnotationPresent)
                .collect(Collectors.toList());
    }

    private boolean isAutoWiredAnnotationPresent(final Constructor<?> constructor) {
        return constructor.isAnnotationPresent(AutoWired.class);
    }

    private Constructor<?> getDefaultConstructor(final Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateSingleAutowiredConstructor(final List<Constructor<?>> constructors) {
        if (constructors.size() > 1) {
            throw new RuntimeException("There are more than one AutoWired annotation.");
        }
    }

    private Object[] getParametersThroughBeanMap(final Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(parameter -> beanMap.getOrDefault(parameter.getName(), parameter))
                .toArray();
        // Class일 수도 있다는 게 문제. 빈 생성을 또 해줘야 한다?
    }

    public <T> T getBean(final Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }

}
