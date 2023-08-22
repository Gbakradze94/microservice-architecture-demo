package com.microservice.resourceservice.cucumber.component;


import com.microservice.resourceservice.api.ResourceController;
import com.microservice.resourceservice.cucumber.e2e.HttpClient;
import com.microservice.resourceservice.service.ResourceServiceImpl;
import com.microservice.resourceservice.service.S3StorageRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ComponentTestsStepDefinition {

    @Autowired
    private S3StorageRepository s3StorageRepository;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private ResourceServiceImpl resourceServiceImpl;
    @Autowired
    private HttpClient httpClient;

    @When("the client makes PUT request to {string}")
    public void the_client_makes_put_request_to(String path) throws Throwable {
        httpClient.performPostRequestToSaveResourceEndpoint(path);
    }

    @Then("the client receives status code {int}")
    public void the_client_receives_status_code(Integer statusCode) throws IOException, SAXException {
        assertSame(HttpStatus.valueOf(statusCode), httpClient.getResponse().getStatusCode());
    }
}
