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
import java.io.PrintWriter;

import static java.lang.System.out;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        out.println("SignUpServlet.init");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out.println("SignUpServlet.doGet");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("sign-up.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        out.println("SignUpServlet.doPost");

        out.println(req.toString());

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        User user = new User(id,pw);
        UserRepository.save(user);

        PrintWriter out = res.getWriter();
        out.print(id+","+pw);
    }
}
