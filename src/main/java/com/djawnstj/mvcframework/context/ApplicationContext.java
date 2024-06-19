package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.annotation.*;
import com.djawnstj.mvcframework.bean.BeanFactory;
import com.djawnstj.mvcframework.boot.web.embbed.tomcat.TomcatWebServer;
import com.djawnstj.mvcframework.boot.web.servlet.Controller;
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
    public static final String APPLICATION_CONTEXT = "applicationContext";

    private final BeanFactory factory;
    public final Set<Class<?>> beanClasses = new HashSet<>();
    public final Map<String, Object> beanMap = new HashMap<>();
    public final Map<String, Method> configurationMap = new HashMap<>();

    public ApplicationContext(final String packageName) {
        logger.debug("packageName = {}", packageName);
        this.factory = new BeanFactory(packageName);
    }

    public void init() {
        Set<Class<?>> componentClasses = factory.scanAnnotationClasses(Component.class);

        beanClasses.addAll(componentClasses);

        createBeansReferenceByConfiguration(componentClasses);

        createBeans(beanClasses);

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            logger.debug(String.valueOf(entry));
        }

        startServer();
    }

    private void startServer() {
        TomcatWebServer server = new TomcatWebServer(this);
        server.start();
    }

    // configuration 을 참조하여 생성
    private void createBeansReferenceByConfiguration(final Set<Class<?>> configClasses) {
        for (Class<?> configClass : configClasses) {
            if (configClass.isAnnotationPresent(Configuration.class)) {
                addConfiguration(configClass.getDeclaredMethods());
            }
        }
    }

    private void addConfiguration(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {

                beanClasses.add(method.getReturnType());

                configurationMap.put(method.getReturnType().getSimpleName(), method);
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

    // 실제 빈 인스턴스가 존재하는지 확인
    private boolean isBeanExist(Class<?> beanClass) {
        return beanMap.containsKey(beanClass.getSimpleName());
    }

    private void createInstance(final Class<?> beanClass) {
        if (isBeanExist(beanClass)) {
            return;
        }

        if (configurationMap.containsKey(beanClass.getSimpleName())) {
            Class<?> declaringClass = configurationMap.get(beanClass.getSimpleName()).getDeclaringClass();
            createInstanceByConfiguration(declaringClass, beanClass.getSimpleName());
            return;
        }

        Constructor<?> constructor = getConstructor(beanClass);

        createInstanceByConstructor(beanClass, constructor);
    }

    private void createInstanceByConstructor(Class<?> beanClass, Constructor<?> constructor) {
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

    private void createInstanceByConfiguration(Class<?> declaringClass, String simpleName) {
        Method method = configurationMap.get(simpleName);
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();

            method.setAccessible(true);

            Object[] parameters = createParameters(parameterTypes);

            Object configInstance = getConstructor(declaringClass).newInstance();

            Object bean = method.invoke(configInstance, parameters);

            Bean annotation = method.getAnnotation(Bean.class);

            if (annotation.name().isBlank()) {
                saveBean(simpleName, bean);
                return;
            }

            saveBean(annotation.name(), bean);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            method.setAccessible(false);
        }
    }

    private Object[] createParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameterTypes[i];

            // Set에 없는경우
            if (!(beanClasses.contains(parameterType))) {
                throw new RuntimeException("파라미터 타입이 없습니다 - " + parameterType.getSimpleName());
            }

            // Set은 위에서 검증 완료, 존재하지만 Instance로 존재하지 않는 경우
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

    private void saveBean(final String beanName, final Object instance) {
        beanMap.put(beanName, instance);
    }

    public <T> T getBean(final Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz.getSimpleName()));
    }

    public <T> Map<String, T> getBeanMap(final Class<T> type) {
        Map<String, T> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (type.isInstance(entry.getValue())) {
                result.put(entry.getKey(), type.cast(entry.getValue()));
            }
        }

        return Collections.unmodifiableMap(result);
    }
}
