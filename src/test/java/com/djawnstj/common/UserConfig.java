package com.djawnstj.common;

import com.djawnstj.mvcframework.annotation.Bean;
import com.djawnstj.mvcframework.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public ProductService productService(final ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

}
