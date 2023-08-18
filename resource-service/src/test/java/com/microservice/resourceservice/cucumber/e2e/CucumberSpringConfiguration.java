package com.microservice.resourceservice.cucumber.e2e;

import com.microservice.resourceservice.config.AmazonS3TestConfiguration;
import com.microservice.resourceservice.service.S3StorageRepository;
import com.microservice.resourceservice.service.S3StorageServiceExtension;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
//@ContextConfiguration(classes = AmazonS3TestConfiguration.class)
//@TestPropertySource("classpath:application.properties")
//@ExtendWith(S3StorageServiceExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {
    @MockBean
    private S3StorageRepository s3StorageRepository;
}
