package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.MvcApplicationMain;
import com.djawnstj.mvcframework.context.ApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

class ControllerConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerConfigTest.class);

    @Test
    @DisplayName("Controller Config Test")
    void controllerConfigTest() throws IOException, ClassNotFoundException, InterruptedException {
        MvcApplicationMain.main(new String[0]);

        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        String uri = "/home";

        List<String> controllerBeanNames = applicationContext.controllerBeanNames;
        logger.debug(String.valueOf(controllerBeanNames));
    }
}