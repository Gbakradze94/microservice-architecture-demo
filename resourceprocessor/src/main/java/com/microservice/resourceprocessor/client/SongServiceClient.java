package com.microservice.resourceprocessor.client;


import com.microservice.resourceprocessor.domain.SongRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class SongServiceClient {
    private static final String PATH = "/api/v1/songs";
    private final WebClient webClient;


    public SongRecord post(SongRecord songRecord) {
        return webClient.post()
                        .uri(uriBuilder -> uriBuilder.path("http://localhost:8080/api/v1/songs").build())
                        .bodyValue(songRecord)
                        .retrieve()
                        .bodyToMono(SongRecord.class)
                        .block();
    }
}
