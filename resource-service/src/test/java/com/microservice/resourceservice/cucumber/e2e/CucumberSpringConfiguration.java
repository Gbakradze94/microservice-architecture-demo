package com.microservice.resourceservice.cucumber.e2e;

import com.microservice.resourceservice.service.S3StorageRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {
    @MockBean
    private S3StorageRepository s3StorageRepository;
}
