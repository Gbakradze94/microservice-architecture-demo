package com.microservice.resourceprocessor.client;

import com.microservice.resourceprocessor.model.SongMetaData;
import com.microservice.resourceprocessor.model.dto.GetSongDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
@Builder
public class SongServiceClient {
    private static final String SONG_SERVICE_PATH = "/api/v1/songs";
    private final WebClient webClient;
    private final RetryProperties retryProperties;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    public Mono<GetSongDTO> post(SongMetaData songMetaData) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(SONG_SERVICE_PATH).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(songMetaData)
                .retrieve()
                .bodyToMono(GetSongDTO.class)
                .transform(reactiveCircuitBreaker::run)
                .retryWhen(Retry.backoff(retryProperties.getMaxAttempts(), Duration.ofMillis(retryProperties.getInitialInterval()))
                        .doBeforeRetry(retrySignal -> log.info("Retrying request: attempt {}", retrySignal.totalRetriesInARow())));
    }
}
