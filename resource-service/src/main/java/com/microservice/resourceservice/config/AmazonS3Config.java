package com.microservice.resourceservice.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.microservice.resourceservice.service.S3StorageRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
@Slf4j
public class AmazonS3Config {
    @Value("${aws.s3.endpoint-url}")
    private String endpointUrl;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.bucket-name}")
    @Getter
    private String bucketName;

    @Bean
    public S3StorageRepository s3StorageRepository() {
        return new S3StorageRepository(amazonS3());
    }

    @Bean
    AmazonS3 amazonS3() {
        log.info("ENDPOINT: " + endpointUrl);
        return AmazonS3ClientBuilder.standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
                .build();
    }
}
