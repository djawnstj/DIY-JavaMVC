package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Component;
import com.djawnstj.mvcframework.annotation.Repository;
import com.djawnstj.mvcframework.annotation.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;

public class DynamicBeanTest {

    @Test
    @DisplayName("조회한 클래스 정보들을 이용해 빈 생성")
    void createBean() {
        final String basePackage = "com.djawnstj.mvcframework";
        ComponentScanner componentScanner = new ComponentScanner();
        BeanFactory beanFactory = new BeanFactory();

        Set<Class<?>> scanSet = componentScanner.scan(basePackage, Component.class);
        Set<Constructor<?>> bean = beanFactory.getBean(scanSet);

        System.out.println(bean.toString());

    }

}
