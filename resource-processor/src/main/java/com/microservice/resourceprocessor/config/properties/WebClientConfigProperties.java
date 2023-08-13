package com.microservice.resourceprocessor.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = WebClientConfigProperties.PREFIX)
@Data
public class WebClientConfigProperties {
    public static final String PREFIX = "web-client";
    private int connectionTimeOut;
    private int responseTimeOut;
    private int readTimeOut;
    private int writeTimeOut;
    private String baseUrl;
}
