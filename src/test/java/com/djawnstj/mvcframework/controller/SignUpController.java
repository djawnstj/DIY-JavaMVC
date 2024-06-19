package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.boot.web.servlet.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpController implements Controller {

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("/sign-up 요청 처리 완료");

        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("sign-up.jsp");
            requestDispatcher.forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
