package com.djawnstj.mvcframework.bean;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanFactory {
    private final Reflections reflections;

    public BeanFactory(final String basePackage) {
        reflections = new Reflections("com.djawnstj.mvcframework", basePackage);
    }

    public Set<Class<?>> scanAnnotationClasses(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> !(type.isAnnotation() && type.isInterface()))
                .collect(Collectors.toSet());
    }
}
