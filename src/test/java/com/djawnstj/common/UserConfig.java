package com.djawnstj.common;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(final UserRepository userRepository) {
        return new UserService(userRepository);
    }

}
