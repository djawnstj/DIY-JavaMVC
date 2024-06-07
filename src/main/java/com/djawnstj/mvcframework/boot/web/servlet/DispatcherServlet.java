package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.boot.web.mvc.InterfaceBeanControllerMapping;
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
    private InterfaceBeanControllerMapping controllerMapping = new InterfaceBeanControllerMapping();
    private ApplicationContext ac;

    @Override
    public void init() throws ServletException {
        super.init();

        Object att = getServletContext().getAttribute(ApplicationContext.APPLICATION_CONTEXT);

        setAttribute((ApplicationContext) att);
    }

    private void setAttribute(ApplicationContext att) {
        ac = att;
        controllerMapping.initControllers(ac.getBeanMap(Controller.class));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String method = req.getMethod();

        logger.debug("process called. uri -> {}, method -> {}", uri, method);

        Object controller = controllerMapping.getController(uri);

        if (controller == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Controller not found for URI: " + uri);
            return;
        }

        // 컨트롤러를 타입에 맞게 실행 시켜 줄 역할의 객체도 필요
        controller.handleRequest(req, resp);
    }
}
