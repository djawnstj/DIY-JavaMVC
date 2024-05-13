package com.djawnstj.mvcframework.bean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BeanFactoryTest {

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void getBean() {
        final String basePackage = "com.djawnstj.mvcframework";
        BeanFactory beanFactory = new BeanFactory(basePackage);
        beanFactory.init();

        Object userService1 = beanFactory.getBean(UserService.class);
        Object userService2 = beanFactory.getBean(UserService.class);

        assertThat(userService1).isSameAs(userService2);
    }

    @Test
    @DisplayName("@Autowired 애너테이션이 붙은 생성자의 파라미터 주입")
    void checkEqualsDependencyAndBean() {
        final String basePackage = "com.djawnstj.mvcframework";
        BeanFactory beanFactory = new BeanFactory(basePackage);
        beanFactory.init();
        UserRepository userRepository = (UserRepository) beanFactory.getBean(UserRepository.class);
        UserService userService = (UserService) beanFactory.getBean(UserService.class);

    }

}
