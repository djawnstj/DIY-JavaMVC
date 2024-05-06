package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
