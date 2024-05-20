package com.djawnstj.mvcframework.bean;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScanner {

//    public List<Class<?>> scan(String basePackage) throws IOException, ClassNotFoundException {
//        List<Class<?>> classes = new ArrayList<>();
//        String path = basePackage.replace(".", "/");
//        File baseDir = new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
//
//        if (baseDir.exists() && baseDir.isDirectory()) {
//            for (File file : baseDir.listFiles()) {
//                if (file.isDirectory()) {
//                    classes.addAll(scan(basePackage + "." + file.getName()));
//                } else if (file.getName().endsWith(".class")) {
//                    String className = basePackage + "." + file.getName().substring(0, file.getName().length() - 6);
//                    classes.add(Class.forName(className));
//                }
//            }
//        }
//        return classes;
//    }

    public Set<Class<?>> scan(final String basePackage, final Class<? extends Annotation> annotation) {
        final Reflections reflections = new Reflections(basePackage);

        return reflections.getTypesAnnotatedWith(annotation)
                .stream().filter(clazz -> !clazz.isAnnotation() && !clazz.isInterface())
                .collect(Collectors.toSet());
    }

}