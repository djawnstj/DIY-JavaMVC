package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private final String packageName;
    private final ComponentScanner componentScanner = new ComponentScanner();
    private final Map<String, Object> beanMap = new HashMap<>();

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    public void init() {
        Set<Class<?>> components = componentScanner.scan(packageName, Component.class);

        createBeanMap(components);
    }

    private void createBeanMap(final Set<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (isConfigurationClass(clazz)) {
                createConfigurationBean(clazz);
            }
            createBean(clazz);
        }
    }

    private boolean isConfigurationClass(final Class<?> clazz) {
        return clazz.isAnnotationPresent(Configuration.class);
    }

    private void createConfigurationBean(final Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (isBeanMethod(method)) {
                createBeanFromMethod(clazz, method);
            }
        }
    }

    private boolean isBeanMethod(final Method method) {
        return method.isAnnotationPresent(Bean.class);
    }

    private void createBeanFromMethod(final Class<?> clazz, final Method method) {
        Object[] parameters = getParametersThroughBeanMap(method.getParameterTypes());

        try {
            method.setAccessible(true);
            Object bean = method.invoke(getBeanOrCreate(clazz), parameters);
            beanMap.put(bean.getClass().getName(), bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            method.setAccessible(false);
        }
    }

    private void createBean(final Class<?> clazz) {
        if (isContainInBeanMap(clazz)) {
            return;
        }

        Constructor<?> constructor = getConstructor(clazz);
        Object[] parameters = getParametersThroughBeanMap(constructor.getParameterTypes());

        try {
            constructor.setAccessible(true);
            Object bean = constructor.newInstance(parameters);
            beanMap.put(constructor.getName(), bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    private boolean isContainInBeanMap(final Class<?> clazz) {
        return beanMap.containsKey(clazz.getName());
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

    private Object[] getParametersThroughBeanMap(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(this::getBeanOrCreate)
                .toArray();
    }

    private Object getBeanOrCreate(final Class<?> parameterType) {
        if (!isContainInBeanMap(parameterType)) {
            createBean(parameterType);
        }
        return beanMap.get(parameterType.getName());
    }

    public <T> T getBean(final Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }

}
