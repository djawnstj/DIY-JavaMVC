package com.djawnstj.mvcframework;

import com.djawnstj.mvcframework.bean.ComponentScanner;
import com.djawnstj.mvcframework.boot.MvcApplication;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MvcApplicationMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MvcApplication.run(MvcApplicationMain.class, args);

        String packageName = "com.djawnstj.mvcframework";

        // 메인 스레드 실행 후, 스캔해서 모든 클래스 정보 가져옴
        ComponentScanner componentScanner = new ComponentScanner();
        List<Class<?>> classes = componentScanner.basicScan(packageName);
        Set<Class<?>> classes1 = componentScanner.reflectionScan(packageName);
    }
}
