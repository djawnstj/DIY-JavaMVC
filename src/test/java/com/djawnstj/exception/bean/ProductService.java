package com.djawnstj.exception.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    @AutoWired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductRepository getProductRepository() {
        return this.productRepository;
    }

}
