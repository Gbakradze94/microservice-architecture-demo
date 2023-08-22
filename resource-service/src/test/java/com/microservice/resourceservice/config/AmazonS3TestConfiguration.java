package com.microservice.resourceservice.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.microservice.resourceservice.service.S3StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;



@TestConfiguration
@Slf4j
public class AmazonS3TestConfiguration {
    @Value("${aws.s3.endpoint-url}")
    private String amazonS3Endpoint;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.bucket-name}")
    private String amazonS3BucketName;
    @Bean
    public S3StorageRepository storageRepository() {
        return new S3StorageRepository(amazonS3TestBean());
    }


    private AmazonS3 amazonS3TestBean() {
        log.info("ENDPOINT: " + amazonS3Endpoint);
        return AmazonS3ClientBuilder.standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonS3Endpoint, region))
                .build();
    }
}
