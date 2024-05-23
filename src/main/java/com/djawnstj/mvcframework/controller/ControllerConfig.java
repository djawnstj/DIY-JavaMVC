package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;
import com.djawnstj.mvcframework.boot.web.servlet.HomeServlet;
import com.djawnstj.mvcframework.boot.web.servlet.SignUpServlet;
import com.djawnstj.mvcframework.boot.web.servlet.UserServlet;

@Configuration
public class ControllerConfig {

    @Bean(name = "/home")
    public HomeServlet homeServlet(){
        return new HomeServlet();
    }

    @Bean(name = "/sign-up")
    public SignUpServlet signUpServlet(){
        return new SignUpServlet();
    }

    @Bean(name = "/user")
    public UserServlet userServlet() {
        return new UserServlet();
    }

}
