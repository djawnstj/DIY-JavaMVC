package com.djawnstj.mvcframework.boot.web.mvc;

import com.djawnstj.mvcframework.boot.web.servlet.Controller;

import java.util.HashMap;
import java.util.Map;

public class InterfaceBeanControllerMapping {
    private Map<String, Controller> controllerBean = new HashMap<>();

    public void initControllers(Map<String, Controller> beanMap) {
        this.controllerBean.putAll(beanMap);
    }

    public Object getController(String url) {
        return controllerBean.get(url);
    }
}
