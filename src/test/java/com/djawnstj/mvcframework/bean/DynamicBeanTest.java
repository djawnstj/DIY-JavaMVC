package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class DynamicBeanTest {

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void createBean() {
        ComponentScanner componentScanner = new ComponentScanner();
        BeanFactory beanFactory = new BeanFactory();

        Set<Class<?>> scanSet = componentScanner.scan(Service.class);

        scanSet.forEach(aClass -> {
            try {
                Object bean = beanFactory.getBean(aClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

}
