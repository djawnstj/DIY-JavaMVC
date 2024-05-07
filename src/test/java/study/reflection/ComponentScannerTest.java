package study.reflection;

import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.bean.BeanFactory;
import com.djawnstj.mvcframework.code.UserRepository;
import com.djawnstj.mvcframework.code.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class ComponentScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("어노테이션 조회 테스트")
    void testAnnotation() {
        String packageName = "com.djawnstj.mvcframework";
        BeanFactory beanFactory = new BeanFactory(packageName);

        Set<Class<?>> classes = beanFactory.scanAnnotation(Component.class);

        boolean contains = classes.contains(UserRepository.class) && classes.contains(UserService.class);
        Assertions.assertTrue(contains);
    }

    @Test
    @DisplayName("빈 등록 및 조회 테스트 - 모든 객체는 싱글톤 패턴으로 구현")
    void addBeanTest() {
        String packageName = "com.djawnstj.mvcframework";
        BeanFactory beanFactory = new BeanFactory(packageName);

        Set<Class<?>> classes = beanFactory.scanAnnotation(Component.class);

        Set<Object> instances = new HashSet<>();

        for (Class<?> clazz : classes) {
            logger.debug(clazz.getName());
            // 인스턴스 생성
//            Object instance = beanFactory.getBean(clazz);
//            instances.add(instance);
        }

        // 두 컬렉션의 사이즈가 같다면 객체도 하나만 생성된 것이기 때문에 싱글톤
        Assertions.assertEquals(classes.size(),instances.size());
    }
}
