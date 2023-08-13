package com.microservice.resourceprocessor.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    private HttpClient httpClient() {
    return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
            .responseTimeout(Duration.ofMillis(3000))
            .doOnConnected(connection -> {
                connection.addHandlerFirst(new ReadTimeoutHandler(3000))
                        .addHandlerFirst(new WriteTimeoutHandler(3000));
            });
    }

    @Bean
    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction) {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(reactorLoadBalancerExchangeFilterFunction)
               // .clientConnector(new ReactorClientHttpConnector();
                .build();
    }
}
