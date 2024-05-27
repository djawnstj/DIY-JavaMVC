package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.context.ApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ControllerConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerConfigTest.class);

    @Test
    @DisplayName("Controller Config Test")
    void controllerConfigTest() {
        ApplicationContext applicationContext = new ApplicationContext("com.djawnstj.mvcframework");
        applicationContext.init();
    }
}