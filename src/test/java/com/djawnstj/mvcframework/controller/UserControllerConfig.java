package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class UserControllerConfig {

    @Bean(name = "/home")
    public HomeController homeController() {
        return new HomeController();
    }
}
