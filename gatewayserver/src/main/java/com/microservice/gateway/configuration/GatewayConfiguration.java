package com.microservice.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(value = {
        ResourceServiceProperties.class,
        SongServiceProperties.class
})
public class GatewayConfiguration {

    @Value("${gateway.service.uri}")
    private String baseUrl;
//    @Bean
//    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
//        return WebClient.builder()
//                .baseUrl(baseUrl)
//                .filter(loadBalancerExchangeFilterFunction)
//                .build();
//    }
}
