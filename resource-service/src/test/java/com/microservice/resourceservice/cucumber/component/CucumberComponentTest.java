package com.microservice.resourceservice.cucumber.component;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/component",
        glue = "com.microservice.resourceservice.cucumber.component"
)
public class CucumberComponentTest {
}
