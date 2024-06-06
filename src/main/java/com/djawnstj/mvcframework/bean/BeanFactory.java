package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.annotation.Configuration;
import com.djawnstj.mvcframework.boot.web.embbed.tomcat.TomcatWebServer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {

    private final String packageName;
    private final ComponentScanner componentScanner = new ComponentScanner();
    private final Set<Class<?>> beanClasses = new HashSet<>();
    private final Map<String, Object> beanMap = new HashMap<>();
    private final Map<String, Method> configBeanMap = new HashMap<>();

    public BeanFactory(final String packageName) {
        this.packageName = packageName;
    }

    public void init() {
        Set<Class<?>> components = componentScanner.scan(packageName, Component.class);

        createBeanClasses(components);
        createBean();

        startServer();
    }

    private void createBeanClasses(final Set<Class<?>> classes) {
        beanClasses.addAll(classes);

        classes.forEach(clazz -> {
            if (isConfigurationClass(clazz)) {
                addConfigurationBean(clazz);
            }
        });
    }

    private boolean isConfigurationClass(final Class<?> clazz) {
        return clazz.isAnnotationPresent(Configuration.class);
    }

    private void addConfigurationBean(final Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (isBeanMethod(method)) {
                beanClasses.add(method.getReturnType());
                configBeanMap.put(method.getReturnType().getName(), method);
            }
        });
    }

    private boolean isBeanMethod(final Method method) {
        return method.isAnnotationPresent(Bean.class);
    }

    private void createBean() {
        beanClasses.forEach(this::createBeanMap);
    }

    private void createBeanMap(final Class<?> clazz) {
        if (isContainInBeanMap(clazz)) {
            return;
        }

        if (!beanClasses.contains(clazz)) {
            throw new RuntimeException("ParameterType is not the target of creating Bean");
        }

        if (isContainInConfigBeanMap(clazz)) {
            createBeanFromMethod(configBeanMap.get(clazz.getName()));
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

    private boolean isContainInConfigBeanMap(final  Class<?> clazz) {
        return configBeanMap.containsKey(clazz.getName());
    }

    private void createBeanFromMethod(final Method method) {
        Object[] parameters = getParametersThroughBeanMap(method.getParameterTypes());

        try {
            method.setAccessible(true);

            String beanName = method.getAnnotation(Bean.class).name();

            Object bean = method.invoke(getBeanOrCreate(method.getDeclaringClass()), parameters);
            if (beanName.isBlank()) {
                beanMap.put(bean.getClass().getName(), bean);
            } else {
                beanMap.put(beanName, bean);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            method.setAccessible(false);
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

    private Object[] getParametersThroughBeanMap(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(this::getBeanOrCreate)
                .toArray();
    }

    private Object getBeanOrCreate(final Class<?> parameterType) {
        createBeanMap(parameterType);

        return beanMap.get(parameterType.getName());
    }

    private void startServer() {
        TomcatWebServer server = new TomcatWebServer(this);
        server.start();
    }

    public <T> T getBean(final Class<T> clazz) {
        return (T) beanMap.get(clazz.getName());
    }

    public boolean isContainBean(final String key) {
        return beanMap.containsKey(key);
    }

    public Object getBean(final String key) {
        return beanMap.get(key);
    }

}
