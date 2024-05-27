package com.nobblecrafts.challenge.devsecopssr.config.test.web.client;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;

public class InterceptableTestRestTemplate extends TestRestTemplate {

    public InterceptableTestRestTemplate(RestTemplateBuilder builder, String rootUri, int port) {
        super(builder.rootUri(rootUri + ":" + port));
    }
    public void addInterceptor(ClientHttpRequestInterceptor interceptor) {
        getRestTemplate().getInterceptors().add(interceptor);
    }
}
