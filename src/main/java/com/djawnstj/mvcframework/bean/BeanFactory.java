package com.djawnstj.mvcframework.bean;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanFactory {
    private final Reflections reflections;

    public BeanFactory(final String basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scanAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> !(type.isAnnotation() && type.isInterface()))
                .collect(Collectors.toSet());
    }

    public Object getBean(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
