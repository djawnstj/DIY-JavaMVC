package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean(name = "/home")
    public HomeController homeController() {
        return new HomeController();
    }

    @Bean(name = "/sign-up")
    public SignUpController signUpController() {
        return new SignUpController();
    }

}
