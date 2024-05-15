package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.annotation.Autowired;
import com.djawnstj.mvcframework.annotation.Component;
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
    // 생성되지 않은 빈들의 목록 String값으로 접근 가능
    // getName() 으로 접근해서 클래스의 정보를 가져온 다음 인스턴스 생성하도록
    private final Map<String, Object> beanMap = new HashMap<>();
    // autowired에 필요한 인스턴스 생성
    // beanClasses에 등록된 클래스들을 실제 인스턴스로 만듬
    private final Map<Class<?>, Object> beans = new HashMap<>();

    // 생성자로 패키지의 모든 클래스중 어노테이션이 되어있는 클래스만 가져옴.
    public ApplicationContext(final String packageName) {
        this.factory = new BeanFactory(packageName);
    }

    public void init() {
        // init시에 빈 팩토리 객체에 내부에 있는 어노테이션된 클래스들을 모두 찾아 가져오는 메서드 실행
        Set<Class<?>> annotationClasses = factory.scanAnnotationClasses(Component.class);
        // 위에 찾은 클래스들을 beanClasses에 저장
        beanClasses.addAll(annotationClasses);
        // 빈을 동적으로 생성하는 메서드 실행
        createBeans(beanClasses);
        createBean(beanClasses);
    }

    private void createBean(final Set<Class<?>> beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            if (isContainsKey(beanClass)) {
                continue;
            }

            createInstance(beanClass);
        }
    }

    private boolean isContainsKey(Class<?> beanClass) {
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

    private Object[] createParameters(Class<?>[] parameterTypes) throws InstantiationException, IllegalAccessException {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameters.length; i++) {
            if (isContainsKey(parameterTypes[i])) {
                parameters[i] = beanMap.get(parameterTypes[i].getSimpleName());
            }

            parameters[i] = parameterTypes[i].newInstance();
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

    private Object saveBean(String beanName, Object instance) {
        return beanMap.put(beanName, instance);
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
