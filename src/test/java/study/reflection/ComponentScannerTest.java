package study.reflection;

import com.djawnstj.mvcframework.code.UserRepository;
import com.djawnstj.mvcframework.code.UserService;
import com.djawnstj.mvcframework.context.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class ComponentScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new ApplicationContext("com.djawnstj.mvcframework");
        applicationContext.init();
    }

    @Test
    @DisplayName("어노테이션 조회 테스트")
    void testAnnotation() {
        Object bean1 = applicationContext.getBean(UserService.class);
        Object bean2 = applicationContext.getBean(UserRepository.class);

        Assertions.assertThat(bean1).isNotNull();
        Assertions.assertThat(bean2).isNotNull();
    }

    @Test
    @DisplayName("빈 등록 및 조회 테스트 - 모든 객체는 싱글톤 패턴으로 구현")
    void addBeanTest() {
        Object bean1 = applicationContext.getBean(UserService.class);
        Object bean2 = applicationContext.getBean(UserService.class);

        Assertions.assertThat(bean1).isNotNull();
        Assertions.assertThat(bean2).isNotNull();
        Assertions.assertThat(bean1).isSameAs(bean2);
    }
}
