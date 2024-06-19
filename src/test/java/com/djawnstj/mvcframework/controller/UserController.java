package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.code.UserService;
import com.djawnstj.mvcframework.annotation.Controller;

@Controller("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    // 회원가입 뷰

    // 화원가입

    // 회원 목록 뷰

}
