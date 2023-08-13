package com.microservice.resourceprocessor.config;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.config.properties.WebClientConfigProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties({WebClientConfigProperties.class, RetryProperties.class})
public class WebClientConfig {

    private HttpClient httpClient(WebClientConfigProperties properties) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectionTimeOut())
                .responseTimeout(Duration.ofMillis(properties.getResponseTimeOut()))
                .doOnConnected(connection ->
                        connection.addHandlerFirst(new ReadTimeoutHandler(properties.getReadTimeOut()))
                                .addHandlerFirst(new WriteTimeoutHandler(properties.getWriteTimeOut())));
    }

    @Bean
    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction,
                               WebClientConfigProperties webClientConfigProperties) {
        return WebClient.builder()
                .baseUrl(webClientConfigProperties.getBaseUrl())
                .filter(reactorLoadBalancerExchangeFilterFunction)
                .clientConnector(
                        new ReactorClientHttpConnector(httpClient(webClientConfigProperties))
                )
                .build();
    }

    @Bean
    public ResourceServiceClient resourceServiceClient(WebClient webClient,
                                                       RetryProperties retryProperties,
                                                       ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
     return ResourceServiceClient.builder()
             .webClient(webClient)
             .retryProperties(retryProperties)
             .reactiveCircuitBreaker(circuitBreakerFactory.create("resource-service"))
             .build();
    }

    @Bean
    public SongServiceClient songServiceClient(WebClient webClient,
                                               RetryProperties retryProperties,
                                               ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        return SongServiceClient.builder()
                .webClient(webClient)
                .retryProperties(retryProperties)
                .reactiveCircuitBreaker(circuitBreakerFactory.create("song-service"))
                .build();
    }
}
