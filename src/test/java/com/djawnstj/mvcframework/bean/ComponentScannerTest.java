package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Component;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class ComponentScannerTest {

    @Test
    @DisplayName("특정 애너테이션이 붙은 클래스만 가져오기")
    void getClassInfo() {
        final String basePackage = "com.djawnstj.mvcframework";

        ComponentScanner componentScanner = new ComponentScanner();
        Set<Class<?>> scanList = componentScanner.scan(basePackage, Component.class);

        assertThat(scanList).contains(UserService.class);
        assertThat(scanList).contains(UserRepository.class);
    }

}
