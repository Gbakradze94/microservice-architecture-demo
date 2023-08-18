package com.microservice.resourceservice.cucumber.e2e;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertSame;


public class E2ETestsStepDefinition {

    @Autowired
    private HttpClient httpClient;

    @When("a client makes a POST request to {string} endpoint")
    public void aClientMakesAPostRequestToSaveResourceEndpoint(String path) throws URISyntaxException {
        httpClient.performPostRequestToSaveResourceEndpoint(path);
    }

    @Then("a client receives status code {int} OK")
    public void aClientReceivesAsStatusCode(int statusCode) {
        assertSame(HttpStatus.valueOf(statusCode), httpClient.getResponse().getStatusCode());
    }


}
