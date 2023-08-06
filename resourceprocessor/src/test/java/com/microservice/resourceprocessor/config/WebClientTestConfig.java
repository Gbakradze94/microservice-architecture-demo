package com.microservice.resourceprocessor.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.client.HttpClient;


@TestConfiguration
public class WebClientTestConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create();
    }
}
