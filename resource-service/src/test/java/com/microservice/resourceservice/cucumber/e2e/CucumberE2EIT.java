package com.microservice.resourceservice.cucumber.e2e;

import cloud.localstack.LocalstackTestRunner;
import cloud.localstack.ServiceName;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@LocalstackDockerProperties(services = {ServiceName.S3})
@CucumberOptions(
        features = "src/test/resources/features/e2e",
        glue = "com.microservice.resourceservice.cucumber.e2e"
)
public class CucumberE2EIT {
}
