package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.MvcApplicationMain;
import com.djawnstj.mvcframework.boot.web.servlet.Controller;
import com.djawnstj.mvcframework.context.ApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

class ControllerConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerConfigTest.class);

    @Test
    @DisplayName("controller 실습 1 - 컨트롤러 빈 등록하기")
    void controllerConfigTest() throws IOException, ClassNotFoundException, InterruptedException {
        MvcApplicationMain.main(new String[0]);

        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();
    }
}