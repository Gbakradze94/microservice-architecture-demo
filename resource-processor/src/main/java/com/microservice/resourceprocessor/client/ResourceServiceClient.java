package com.microservice.resourceprocessor.client;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
@Builder
public class ResourceServiceClient {

    private static final String RESOURCE_SERVICE_PATH = "/api/v1/resources";
    private static final String ID_PATH_PARAM = "/{id}";
    private final WebClient webClient;
    private final RetryProperties retryProperties;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    public Flux<DataBuffer> getById(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                                .path(RESOURCE_SERVICE_PATH)
                                .path(ID_PATH_PARAM)
                                .build())
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToFlux(DataBuffer.class)
                .transform(reactiveCircuitBreaker::run)
                .retryWhen(Retry.backoff(retryProperties.getMaxAttempts(),
                        Duration.ofMillis(retryProperties.getInitialInterval()))
                        .doBeforeRetry(retrySignal ->
                                log.info("Retrying request: attempt {}",
                                        retrySignal.totalRetriesInARow())
                        )
                );
    }
}
