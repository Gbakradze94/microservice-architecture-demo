package com.microservice.resourceservice.cucumber.component;

import cloud.localstack.ServiceName;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.microservice.resourceservice.service.S3StorageRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@LocalstackDockerProperties(services = {ServiceName.S3})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {
    @MockBean
    private S3StorageRepository s3StorageRepository;

}
