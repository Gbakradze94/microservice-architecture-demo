package com.microservice.resourceservice.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.microservice.resourceservice.service.S3StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;


@TestConfiguration
@Slf4j
public class AmazonS3TestConfiguration {
//    @Value("${aws.s3.endpoint-url}")
//    private String endpointUrl;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//    @Value("${aws.s3.bucket-name}")
//    @Getter
//    private String bucketName;
//
//    @Bean
//    AmazonS3 amazonS3() {
//        log.info("ENDPOINT: " + endpointUrl);
//        return AmazonS3ClientBuilder.standard()
//                .withPathStyleAccessEnabled(true)
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
//                .build();
//    }
//
//
//    @Bean
//    AmazonS3Config amazonS3Config() {
//        return new AmazonS3Config();
//    }
//
//    @Bean
//    S3StorageService s3StorageService() {
//        return new S3StorageService(amazonS3(),amazonS3Config());
//    }
//
//
//    @Value("${aws.s3.endpoint}")
//    private String amazonS3Endpoint;
//    @Value("${aws.s3.credentials.access-key}")
//    private String amazonS3AccessKey;
//    @Value("${aws.s3.credentials.secret-key}")
//    private String amazonS3SecretKey;
//    @Value("${aws.s3.bucket-name}")
//    private String amazonS3BucketName;

    @Value("${aws.s3.endpoint-url}")
    private String amazonS3Endpoint;

    @Value("${aws.s3.region}")
    private String region;
//    @Value("${aws.s3.credentials.access-key}")
//    private String amazonS3AccessKey;
//    @Value("${aws.s3.credentials.secret-key}")
//    private String amazonS3SecretKey;
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

//    private StaticCredentialsProvider getStaticCredentialsProvider() {
//        return StaticCredentialsProvider.create(getAwsBasicCredentials());
//    }


//    private AwsBasicCredentials getAwsBasicCredentials() {
//        return AwsBasicCredentials.create(amazonS3AccessKey,
//                amazonS3SecretKey);
//    }
}
