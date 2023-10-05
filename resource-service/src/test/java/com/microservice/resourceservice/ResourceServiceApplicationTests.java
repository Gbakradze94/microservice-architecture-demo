package com.microservice.resourceservice;

import com.microservice.resourceservice.config.AmazonS3TestConfiguration;
import com.microservice.resourceservice.util.S3StorageServiceExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@ExtendWith(S3StorageServiceExtension.class)
@ContextConfiguration(classes = AmazonS3TestConfiguration.class)
class ResourceServiceApplicationTests {
    @Test
    void contextLoads() {
    }
}
