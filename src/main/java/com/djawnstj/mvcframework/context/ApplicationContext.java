package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.annotation.Autowired;
import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.annotation.Configuration;
import com.djawnstj.mvcframework.bean.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

// 빈을 생성하는 과정
// 1. 모든 어노테이션을 가져온다. - 컴포넌트 어노테이션이 최상위 이기 때문에 컴포넌트 어노테이션만 가져오면 됨.
// 2. 어노테이션을 통해 가져온 클래스들의 모음을 beanClasses 라고 하고 set으로 저장한다.
// 3. 반복문을 돌면서 autowired 어노테이션들을 찾아준다. 생성자 필드 메서드
// 4. 찾은 생성자, 필드, 메서드가 필요로 하는 매개변수 생성하고 넣어준다
// 5. 필요로 하는 매개변수의 타입을 beanClasses에서 찾아서 생성하고 주입한다.

public class ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    private final BeanFactory factory;
    private final Set<Class<?>> beanClasses = new HashSet<>();
    public final Map<String, Object> beanMap = new HashMap<>();
    public final Map<Class<?>, Object> configurationMap = new HashMap<>();

    public ApplicationContext(final String packageName) {
        this.factory = new BeanFactory(packageName);
    }

    public void init() {
        Set<Class<?>> annotationClasses = factory.scanAnnotationClasses(Component.class);

        beanClasses.addAll(annotationClasses);

        createBeansReferenceByConfiguration(annotationClasses);

        createBeans(beanClasses);
    }

    private void createBeansReferenceByConfiguration(final Set<Class<?>> annotationClasses) {
        for (Class<?> annotationClass : annotationClasses) {
            if (annotationClass.isAnnotationPresent(Configuration.class)) {
                try {
                    Object instance = annotationClass.getDeclaredConstructor().newInstance();
                    Method[] methods = annotationClass.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Bean.class)) {
                            // 리턴 타입, 메소드
                            configurationMap.put(method.getReturnType(),method);
//                            Class<?>[] parameterTypes = method.getParameterTypes();
//                            Object bean;
//                            if (parameterTypes.length > 0) {
//                                Object[] parameters = createParameters(parameterTypes);
//                                bean = method.invoke(instance, parameters);
//                            } else {
//                                bean = method.invoke(instance);
//                            }
//                            saveBean(method.getName(), bean);
                        }
                    }

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void createBeans(final Set<Class<?>> beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            if (isBeanExist(beanClass)) {
                continue;
            }

            createInstance(beanClass);
        }
    }

    private boolean isBeanExist(Class<?> beanClass) {
        return beanMap.containsKey(beanClass.getSimpleName());
    }

    private void createInstance(final Class<?> beanClass) {
        Constructor<?> constructor = getConstructor(beanClass);

        try {
            Object[] parameters = createParameters(constructor.getParameterTypes());

            constructor.setAccessible(true);

            Object instance = constructor.newInstance(parameters);

            saveBean(beanClass.getSimpleName(), instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    private Object[] createParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameterTypes[i];

            // 클래스에 없거나, config에도 없거나 둘다 없을때
            if (!(beanClasses.contains(parameterType)) ) {
                throw new RuntimeException("파라미터 타입이 없습니다 - " + parameterType.getSimpleName());
            }

            if (!isBeanExist(parameterType)) {
                createInstance(parameterType);
            }

            parameters[i] = beanMap.get(parameterType.getSimpleName());
        }

        return parameters;
    }

    private Constructor<?> getConstructor(final Class<?> beanClass) {
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        if (declaredConstructors.length == 1) {
            return declaredConstructors[0];
        }

        return getAutowiredConstructor(declaredConstructors);
    }

    private Constructor<?> getAutowiredConstructor(final Constructor<?>[] constructors) {
        List<Constructor<?>> autowiredConstructors = new ArrayList<>();

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                autowiredConstructors.add(constructor);
            }
        }

        if (autowiredConstructors.size() != 1) {
            throw new RuntimeException("autowired 생성자가 1개가 아닙니다.");
        }

        return autowiredConstructors.get(0);
    }

    private void saveBean(String beanName, Object instance) {
        beanMap.put(beanName, instance);
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz.getSimpleName()));
    }
}
