package com.djawnstj.mvcframework;

import com.djawnstj.mvcframework.boot.MvcApplication;

import java.io.IOException;

public class MvcApplicationMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MvcApplication.run(MvcApplicationMain.class, args);
    }
}
