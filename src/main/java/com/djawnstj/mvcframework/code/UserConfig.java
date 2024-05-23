package com.djawnstj.mvcframework.code;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(final UserRepository userRepository, final PayService payService) {
        return new UserService(userRepository, payService);
    }

    @Bean
    public PayRepository payRepository() {
        return new PayRepository();
    }

    @Bean
    public PayService payService(final PayRepository payRepository) {
        return new PayService(payRepository);
    }
}
