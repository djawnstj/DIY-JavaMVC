package com.djawnstj.mvcframework.boot.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

    void handleRequest(HttpServletRequest req, HttpServletResponse res);
}
