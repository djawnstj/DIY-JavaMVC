package com.djawnstj.mvcframework.config;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;
import com.djawnstj.mvcframework.code.UserRepository;
import com.djawnstj.mvcframework.code.UserService;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(final UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
