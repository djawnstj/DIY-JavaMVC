package com.djawnstj.mvcframework.code;

public class PayService {
    public PayRepository payRepository;

    public PayService(PayRepository payRepository) {
        this.payRepository = payRepository;
    }
}
