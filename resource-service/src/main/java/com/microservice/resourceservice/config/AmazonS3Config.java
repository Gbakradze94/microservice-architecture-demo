package com.microservice.resourceservice.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class AmazonS3Config {
    @Value("${aws.s3.endpoint-url}")
    private String endpointUrl;

    @Bean
    AmazonS3 amazonS3() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpointUrl,
                Regions.US_EAST_1.getName());
        return AmazonS3ClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).withPathStyleAccessEnabled(true).build();
    }
}
