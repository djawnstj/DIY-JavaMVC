package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Component;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BeanFactoryTest {

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void createBean() {
        final String basePackage = "com.djawnstj.mvcframework";
        BeanFactory beanFactory = new BeanFactory(basePackage);
        beanFactory.init(Component.class);

        Object userService1 = beanFactory.getBean(UserService.class);
        Object userService2 = beanFactory.getBean(UserService.class);

        assertThat(userService1).isSameAs(userService2);
    }

}
