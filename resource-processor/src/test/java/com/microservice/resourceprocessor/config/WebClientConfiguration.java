package com.microservice.resourceprocessor.config;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.config.properties.WebClientConfigProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@TestConfiguration
@EnableConfigurationProperties({WebClientConfigProperties.class, RetryProperties.class})
public class WebClientConfiguration {
    private HttpClient httpClient(WebClientConfigProperties properties) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectionTimeOut())
                .responseTimeout(Duration.ofMillis(properties.getResponseTimeOut()))
                .doOnConnected(connection -> connection
                        .addHandlerFirst(new ReadTimeoutHandler(properties.getReadTimeOut(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(properties.getWriteTimeOut(), TimeUnit.MILLISECONDS))
                );
    }

    @Bean
    public WebClient webClient(WebClientConfigProperties properties) {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient(properties)))
                .build();
    }

    @Bean
    public ResourceServiceClient resourceServiceClient(WebClient webClient, RetryProperties retryProperties,
                                                       ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        return new ResourceServiceClient(webClient, retryProperties, circuitBreakerFactory.create("resource-service"));
    }

    @Bean
    public SongServiceClient songServiceClient(WebClient webClient, RetryProperties retryProperties,
                                               ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        return new SongServiceClient(webClient, retryProperties, circuitBreakerFactory.create("song-service"));
    }
}
