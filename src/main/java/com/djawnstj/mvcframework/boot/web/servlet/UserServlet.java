package com.djawnstj.mvcframework.boot.web.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.djawnstj.mvcframework.boot.web.servlet.user.User;
import com.djawnstj.mvcframework.boot.web.servlet.user.UserRepository;

@WebServlet("/users")
public class UserServlet extends HttpServlet{

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("User init called.");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("User service called.");
        // final RequestDispatcher requestDispatcher = req.getRequestDispatcher("users.jsp");
        // requestDispatcher.forward(req, resp);
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("User doGet called.");

        UserRepository userRepository = UserRepository.getInstance();
        Collection<User> userList = userRepository.findAllUsers();
        req.setAttribute("users", userList);

        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("users.jsp");
        requestDispatcher.forward(req, resp);

        // super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("User doPost called.");
        super.doPost(req, resp);
    }

}
