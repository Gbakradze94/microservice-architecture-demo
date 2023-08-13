package com.microservice.resourceprocessor.client;

import com.microservice.resourceprocessor.domain.SongMetaData;
import com.microservice.resourceprocessor.domain.SongRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SongServiceClient {
    private static final String PATH = "/api/v1/songs";
    private final WebClient webClient;

    public Optional<SongRecord> post(SongMetaData songMetaData) {
        return Optional.ofNullable(webClient.post()
                .uri(uriBuilder -> uriBuilder.path("http://localhost:8080/api/v1/songs").build())
                .bodyValue(songMetaData)
                .retrieve()
                .bodyToMono(SongRecord.class)
                .block());
    }
}
