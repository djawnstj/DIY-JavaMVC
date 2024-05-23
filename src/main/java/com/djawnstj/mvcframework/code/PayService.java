package com.djawnstj.mvcframework.code;

public class PayService {

    public PayRepository payRepository;

    public PayService(final PayRepository payRepository) {
        this.payRepository = payRepository;
    }
}