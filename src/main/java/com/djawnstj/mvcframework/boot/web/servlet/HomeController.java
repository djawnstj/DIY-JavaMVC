package com.djawnstj.mvcframework.boot.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController implements Controller{

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("로그인 처리");

        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
