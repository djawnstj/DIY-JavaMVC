package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.boot.web.servlet.user.User;
import com.djawnstj.mvcframework.boot.web.servlet.user.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        System.out.println("init called.");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet.doGet");
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
        requestDispatcher.forward(req, resp);
    }
}
