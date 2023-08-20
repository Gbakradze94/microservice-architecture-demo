package com.microservice.resourceservice.cucumber.e2e;

import com.microservice.resourceservice.domain.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.net.URI;
import java.net.URISyntaxException;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Slf4j
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

    private static final String URL = "http://localhost:";

    @LocalServerPort
    private int serverPort;

    @Getter
    private ResponseEntity<Resource> response = null;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public void performPostRequestToSaveResourceEndpoint(String path) throws URISyntaxException {
        String url = URL + serverPort + path;
        RequestEntity<MultiValueMap<String, Object>> requestBody = buildRequest(url);
        response = testRestTemplate.exchange(requestBody, Resource.class);
    }

    private RequestEntity<MultiValueMap<String, Object>> buildRequest(String url) throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("multipartFile", new FileSystemResource("src/test/resources/features/e2e/sample-12s.mp3"));

        return RequestEntity.put(new URI(url))
                .headers(httpHeaders)
                .body(parameters);
    }
}
