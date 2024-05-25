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
    private final HashMap<String, Object> beanMap = new HashMap<>();

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    public void init() {
        Set<Class<?>> componentSet = componentScanner.scan(packageName, Component.class);
        Set<Class<?>> configurationSet = componentScanner.scan(packageName, Configuration.class);

        createComponentBeanMap(componentSet);
        createConfigurationBeanMap(configurationSet);

    }

    private void createComponentBeanMap(final Set<Class<?>> classSet) {
        classSet.stream()
                .filter(this::isNotContainInBeanMap)
                .forEach(this::createComponentBean);
    }

    private void createConfigurationBeanMap(final Set<Class<?>> classSet) {
        classSet.stream()
                .filter(this::isNotContainInBeanMap)
                .forEach(this::createConfigurationBean);
    }

    private boolean isNotContainInBeanMap(final Class<?> clazz) {
        return !beanMap.containsKey(clazz.getName());
    }

    private void createComponentBean(final Class<?> clazz) {
        Constructor<?> constructor = getConstructor(clazz);
        Object[] parameters = getParametersThroughBeanMap(constructor);

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
                .map(this::getBeanOrCreate)
                .toArray();
    }

    private Object[] getParametersThroughBeanMap(final Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(this::getBeanOrCreate)
                .toArray();
    }

    private Object getBeanOrCreate(final Class<?> parameterType) {
        if (isNotContainInBeanMap(parameterType)) {
            createComponentBean(parameterType);
        }
        return beanMap.get(parameterType.getName());
    }

    private void createConfigurationBean(final Class<?> clazz) {
        List<Method> beanMethods = findBeanMethods(clazz);

        for (Method beanMethod : beanMethods) {
            Object[] parameters = getParametersThroughBeanMap(beanMethod);

            try {
                beanMethod.setAccessible(true);
                Object bean = beanMethod.invoke(clazz.getDeclaredConstructor().newInstance(), parameters);
                beanMap.put(bean.getClass().getName(), bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                beanMethod.setAccessible(false);
            }
        }
    }

    private List<Method> findBeanMethods(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .collect(Collectors.toList());
    }

    public <T> T getBean(final Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }

}
