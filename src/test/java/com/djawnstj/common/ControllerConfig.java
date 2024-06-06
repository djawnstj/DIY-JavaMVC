package com.djawnstj.common;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean(name = "/pay")
    public PayController payController() {
        return new PayController();
    }

}

