package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.web.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private static List<User> users = new ArrayList<>();

    public static List<User> getUserList() {
        return users;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        if(id == null || id.isEmpty() || pw == null || pw.isEmpty()) {
            req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
        }

        users.add(new User(id, pw));
        resp.sendRedirect("users");
    }
}
