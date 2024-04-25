package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.MvcApplicationMain;
import com.djawnstj.mvcframework.boot.web.servlet.user.User;
import com.djawnstj.mvcframework.boot.web.servlet.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


class SignUpServletTest {

    @Test
    void SIGNUP_SERVLET_RESPONSE() throws IOException, InterruptedException {
        // given
        MvcApplicationMain.main(new String[0]);
        String url = "http://localhost:8080/sign-up";
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void SIGNUP_TEST() throws IOException {
        // given
        MvcApplicationMain.main(new String[0]);
        String url = "http://localhost:8080/sign-up";
        User user = new User("user1", "1234");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(url);

        // when
        // request
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", user.getId()));
        params.add(new BasicNameValuePair("pw", user.getPw()));
        postRequest.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(postRequest);

        // response
        String resBody = EntityUtils.toString(response.getEntity());
        String[] responseParts = resBody.split(",");
        String resId = responseParts[0];
        String resPw = responseParts[1];

        // then
        Assertions.assertThat(user.getId()).isEqualTo(resId);
        Assertions.assertThat(user.getPw()).isEqualTo(resPw);
        Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}