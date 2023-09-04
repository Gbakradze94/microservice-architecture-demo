package com.microservice.gateway.router;

import com.microservice.gateway.configuration.ResourceServiceProperties;
import com.microservice.gateway.configuration.SongServiceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties({
        ResourceServiceProperties.class,
        SongServiceProperties.class
})
public class Router {
    @Value("${spring.application.name}")
    private String applicationName;

//    @Bean
//    RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder,
//                        ResourceServiceProperties resourceServiceProperties,
//                        SongServiceProperties songServiceProperties) {
//        return routeLocatorBuilder.routes()
//                .route("get-resource-by-id", route -> route
//                        .method(HttpMethod.GET).and())
//    }
}
