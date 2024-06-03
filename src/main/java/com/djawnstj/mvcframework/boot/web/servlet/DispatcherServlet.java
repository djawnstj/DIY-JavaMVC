package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> controllerBean = new HashMap<>();
    private ApplicationContext ac;

    @Override
    public void init() throws ServletException {
        super.init();

        Object att = getServletContext().getAttribute(ApplicationContext.APPLICATION_CONTEXT);

        ac = (ApplicationContext) att;

        controllerBean = ac.getBeanMap(Controller.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Controller controller = controllerBean.get(uri);
        if (controller != null) {
            controller.handleRequest(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Controller not found for URI: " + uri);
        }
    }
}
