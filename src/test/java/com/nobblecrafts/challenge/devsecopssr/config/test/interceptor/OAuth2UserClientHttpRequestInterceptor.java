package com.nobblecrafts.challenge.devsecopssr.config.test.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class OAuth2UserClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String token;
    private final String cookieName;

    public OAuth2UserClientHttpRequestInterceptor(String cookieName, String token) {
        this.token = token;
        this.cookieName = cookieName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Cookie", cookieName + "=" + token);
        return execution.execute(request, body);
    }
}
