package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.boot.web.servlet.user.User;
import com.djawnstj.mvcframework.boot.web.servlet.user.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("SignUpServlet.init");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SignUpServlet.doGet");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("sign-up.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SignUpServlet.doPost");
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        User user = new User(id,pw);
        UserRepository.save(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
        requestDispatcher.forward(req, resp);
    }
}
