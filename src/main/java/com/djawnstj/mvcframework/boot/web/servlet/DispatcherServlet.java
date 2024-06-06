package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.bean.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String requestURI = req.getRequestURI();

        BeanFactory beanFactory = (BeanFactory) getServletContext().getAttribute("BeanFactory");
        System.out.println("BeanFactory: " + beanFactory);

        if (beanFactory.isContainBean(requestURI)) {
            Object bean = beanFactory.getBean(requestURI);
        } else {
            // 404
        }

        super.service(req, resp);
    }
}
