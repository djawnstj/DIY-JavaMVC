package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.annotation.Autowired;
import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.bean.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    // 피드백 내용중에 main에 프로덕션 코드로 존재해야된다는 피드백 받음
    // 테스트 코드에서 생성하는게 아니라, 실행시에 자동으로 넣어 줘야됨
    // ApplicationContext - 빈을 생성하고 관리하는 객체
    // 테스트 코드 실행시 - new ApplicationContext(basePackage); 이런식으로 했을때 자동으로 빈을 생성하는 객체를 구현

    // 구현 기능
    // 생성시에 빈에 등록된 클래스들을 모음 - 리스트, 정적인 클래스
    // 이후 클래스들을 모은 리스트내부를 모두 동적으로 객체 생성

    // 빈 팩토리 객체
    private final BeanFactory factory;
    // 빈에 등록될 클래스들을 모아놓은 리스트
    private final Set<Class<?>> beanClasses = new HashSet<>();
    private Map<Class<?>, Object> beans = new HashMap<>();

    // 생성자로 패키지의 모든 클래스중 어노테이션이 되어있는 클래스만 가져옴.
    public ApplicationContext(final String packageName) {
        this.factory = new BeanFactory(packageName);
    }

    public void init() {
        // init시에 빈 팩토리 객체에 내부에 있는 어노테이션된 클래스들을 모두 찾아 가져오는 메서드 실행
        Set<Class<?>> annotationClasses = factory.scanAnnotationClasses(Component.class);
        findAutowiredAnnotations(annotationClasses);
        // 위에 찾은 클래스들을 beanClasses에 저장
        beanClasses.addAll(annotationClasses);
        // 빈을 동적으로 생성하는 메서드 실행
        createBeans(beanClasses);
    }

    private void createBeans(Set<Class<?>> classes) {
        // 컴포넌트 어노테이션이 적용된 모든 클래스를 저장한 Set
        for (Class<?> beanClass : classes) {
            if (!beans.containsKey(beanClass)) {
                try {
                    Object instance = beanClass.getDeclaredConstructor().newInstance();
                    // {클래스 : 인스턴스} 로 hashmap에 저장
                    beans.put(beanClass, instance);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // @Autowired 어노테이션을 모두 찾는다.
    // 클래스의 모든 정보를 가져온다.
    public void findAutowiredAnnotations(Set<Class<?>> annotationClasses) {
        for (Class<?> annotationClass : annotationClasses) {
            Field[] declaredFields = annotationClass.getDeclaredFields();
            Constructor<?>[] declaredConstructors = annotationClass.getDeclaredConstructors();
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
