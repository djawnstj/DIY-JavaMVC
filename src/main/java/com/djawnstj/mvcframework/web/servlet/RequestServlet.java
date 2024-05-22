package com.djawnstj.mvcframework.web.servlet;

import com.djawnstj.mvcframework.web.domain.User;
import com.djawnstj.mvcframework.web.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class RequestServlet extends HttpServlet { // 분배하는 서블릿 : dispatcherServlet

    private static final Logger log = LoggerFactory.getLogger(RequestServlet.class);

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void service(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = req.getRequestURI();

        log.debug("method: {}, uri: {}", method, uri);

        if ("/sign-up".equals(uri) && "GET".equals(method)) {
            System.out.println("1번");
            req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
        } else if ("/sign-up".equals(uri) && "POST".equals(method)) {
            System.out.println("2번");
            String id = req.getParameter("id");
            String pw = req.getParameter("pw");

            if (id == null || id.isEmpty() || pw == null || pw.isEmpty()) {
                req.getRequestDispatcher("sign-up.jsp").forward(req, resp);
            }
            userRepository.addUser(id, pw);
            resp.sendRedirect("users");
        } else if ("/users".equals(uri) && "GET".equals(method)) {
            System.out.println("3번");
            req.setAttribute("users", userRepository.getUsers());
            req.getRequestDispatcher("users.jsp").forward(req, resp);
        } else {
            System.out.println("4번");
            req.getRequestDispatcher("404.jsp").forward(req, resp);
        }
    }
}
