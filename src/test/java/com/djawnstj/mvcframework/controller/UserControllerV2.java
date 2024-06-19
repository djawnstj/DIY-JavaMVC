package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.annotation.Controller;
import com.djawnstj.mvcframework.code.UserService;

@Controller("users")
public class UserControllerV2 {

    private final UserService userService;

    public UserControllerV2(final UserService userService) {
        this.userService = userService;
    }

    // 회원가입 뷰

    // 화원가입

    // 회원 목록 뷰

}
