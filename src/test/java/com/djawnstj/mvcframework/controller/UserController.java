package com.djawnstj.mvcframework.controller;

import code.UserService;
import com.djawnstj.mvcframework.boot.web.servlet.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController implements Controller {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse res) {

    }

}
