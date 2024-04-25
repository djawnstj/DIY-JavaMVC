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

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        System.out.println("SignUp init called.");
        super.init(config);
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SignUp service called.");
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("sign-up.jsp");
        requestDispatcher.forward(req, resp);
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SignUp doGet called.");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SignUp doPost called.");

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        User newUser = new User(id, pw);
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.registerUser(newUser);

        super.doPost(req, resp);
    }

}