package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.MvcApplicationMain;
import com.djawnstj.mvcframework.boot.web.servlet.Controller;
import com.djawnstj.mvcframework.context.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

class ControllerConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerConfigTest.class);

    @BeforeEach
    public void beforeEach() throws IOException, ClassNotFoundException {
        MvcApplicationMain.main(new String[0]);
    }

    @Test
    @DisplayName("컨트롤러 빈 등록하기 - 성공")
    void controllerConfigTestSuccess() throws IOException, InterruptedException {
        String url = "http://localhost:8080";
        String uri = "/home";
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("컨트롤러 빈 등록하기 - 성공 2")
    void controllerConfigTestSuccess2() throws IOException, InterruptedException {
        String url = "http://localhost:8080";
        String uri = "/sign-up";
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("컨트롤러 빈 등록하기 - 실패")
    void controllerConfigTestFail() throws IOException, InterruptedException {
        String url = "http://localhost:8080";
        String uri = "/test"; // 없는 uri
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("컨트롤러 빈 등록하기 - test")
    void controllerConfigTestFail2() throws IOException, InterruptedException {
        String url = "http://localhost:8080";
        String uri = "/users/home"; // 없는 uri
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }
}