package com.microservice.resourceprocessor.client;

import com.microservice.resourceprocessor.util.CustomMediaType;
import com.microservice.resourceprocessor.util.DataBufferFluxToFileConverter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ResourceServiceClient {

    private final WebClient webClient;
    private final DataBufferFluxToFileConverter dataBufferFluxToFileConverter;

    public Optional<File> getById(Long id) {
        Flux<DataBuffer> dataBufferFlux = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("http://localhost:8081/api/v1/resources")
                        .path("/{id}")
                        .build(id))
                .accept(MediaType.valueOf(
                        Objects.requireNonNull(CustomMediaType.getMediaTypeByExtension(HttpHeaders.ACCEPT))
                                .getMimeType())
                )
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        return Optional.ofNullable(dataBufferFluxToFileConverter.convert(dataBufferFlux));
    }
}
