package com.microservice.resourceservice.cucumber.e2e;

import com.amazonaws.services.s3.AmazonS3Client;
import com.microservice.resourceservice.service.S3StorageRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;


public class E2ETestsStepDefinition {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private S3StorageRepository s3StorageRepository;

    @When("a client makes a POST request to {string} endpoint")
    public void aClientMakesAPostRequestToSaveResourceEndpoint(String path) throws URISyntaxException {
        httpClient.performPostRequestToSaveResourceEndpoint(path);
    }

    @Then("a client receives status code {int} OK")
    public void aClientReceivesAsStatusCode(int statusCode) {
        assertSame(HttpStatus.valueOf(statusCode), httpClient.getResponse().getStatusCode());
    }

    @And("a client receives the resource id")
    public void aClientReceivesTheResourceId() {
        assertEquals(1, (int) httpClient.getResponse().getBody().getId());
    }
}
