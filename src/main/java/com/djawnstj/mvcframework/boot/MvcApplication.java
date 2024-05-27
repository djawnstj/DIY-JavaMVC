package com.djawnstj.mvcframework.boot;

import com.djawnstj.mvcframework.boot.web.embbed.tomcat.TomcatWebServer;
import com.djawnstj.mvcframework.boot.web.server.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MvcApplication {
    private static final Logger log = LoggerFactory.getLogger(MvcApplication.class);
    public static void run(final Class<?> configClass, final String[] args) {
        log.debug("run args = {}", List.of(args));

        final WebServer webServer = new TomcatWebServer();

        webServer.start();
    }
}
