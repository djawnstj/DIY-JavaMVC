package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.context.ApplicationContext;
import com.djawnstj.mvcframework.context.utils.WebApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final Map<String, Controller> servletBean = new HashMap<>();
    private ApplicationContext ac;

    @Override
    public void init() throws ServletException {
        super.init();

        ac = ApplicationContext.getApplicationContext("code");

        if(ac == null){
            throw new ServletException("ApplicationContext not initialized");
        }

        Map<String, Object> beanMap = ac.getBeanMap();

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (entry.getValue() instanceof Controller) {
                servletBean.put(entry.getKey(), (Controller) entry.getValue());
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Controller controller = servletBean.get(uri);
        if (controller != null) {
            controller.handleRequest(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Controller not found for URI: " + uri);
        }
    }
}
