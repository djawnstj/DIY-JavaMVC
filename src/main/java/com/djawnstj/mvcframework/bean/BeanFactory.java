package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Component;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanFactory {

    private final String packageName;
    private final ComponentScanner componentScanner = new ComponentScanner();
    private final HashMap<String, Object> beanMap = new HashMap<>();

    public void init() {
        Set<Class<?>> scanSet = componentScanner.scan(packageName, Component.class);

        List<Constructor<?>> autoWiredConstructors = getAutoWiredConstructors(scanSet);
        for (Constructor<?> constructor : autoWiredConstructors) {
            Constructor<?> autoWiredConstructor = getAutoWired(constructor.getDeclaringClass());
            putInBeanMap(autoWiredConstructor);
        }

    }

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    private List<Constructor<?>> getAutoWiredConstructors(final Set<Class<?>> classSet) {
        return classSet.stream()
                .filter(clazz -> !beanMap.containsKey(clazz.getName()))
                .flatMap(this::getConstructorsMoreThanOne)
                .collect(Collectors.toList());
    }

    private Stream<Constructor<?>> getConstructorsMoreThanOne(final Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 1) {
            try {
                putInBeanMap(constructors[0]);
                return Stream.empty();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return Arrays.stream(constructors);
    }

    private Constructor<?> getAutoWired(final Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        List<Constructor<?>> autowiredConstructors = new ArrayList<>();

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(AutoWired.class)) {
                autowiredConstructors.add(constructor);
            }
        }

        if (autowiredConstructors.size() == 0) {
            try {
                autowiredConstructors.add(clazz.getDeclaredConstructor());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        if (autowiredConstructors.size() > 1) {
            throw new RuntimeException();
        }

        return autowiredConstructors.get(0);
    }

    private void putInBeanMap(final Constructor<?> autoWiredConstructor) {
        autoWiredConstructor.setAccessible(true);

        try {
            if (autoWiredConstructor.getParameterCount() == 0) {
                beanMap.put(autoWiredConstructor.getName(), autoWiredConstructor.newInstance());
                return;
            }

            List<Object> params = getParametersThroughBeanMap(autoWiredConstructor);

            beanMap.put(autoWiredConstructor.getName(), autoWiredConstructor.newInstance(params.toArray()));

        } catch (Exception e) {
            throw new RuntimeException("There are more than one AutoWired annotation.");
        }

    }

    private List<Object> getParametersThroughBeanMap(final Constructor<?> autoWiredConstructor) {
        Class<?>[] parameterTypes = autoWiredConstructor.getParameterTypes();
        List<Object> params = new ArrayList<>();

        for (Class<?> param : parameterTypes) {
            params.add(beanMap.getOrDefault(param.getName(), param));
        }
        return params;
    }

    public <T> T getBean(final Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }

}
