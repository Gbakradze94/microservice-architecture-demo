package com.microservice.resourceprocessor.util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@RequiredArgsConstructor
public class DataBufferFluxToFileConverter implements Converter<Flux<DataBuffer>, File> {
    private final CustomMediaType customMediaType;

    @SneakyThrows
    @Override
    public File convert(Flux<DataBuffer> dataBufferFlux) {
        return writeDataBuffer(dataBufferFlux, customMediaType).block();
    }

    public Mono<File> writeDataBuffer(Flux<DataBuffer> dataBufferFlux, CustomMediaType customMediaType) throws IOException {
        Path directoryPath = Paths.get("src/main/resources", "temp");
        if(!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath).toFile().deleteOnExit();
        }

        Path filePath = directoryPath.resolve(System.currentTimeMillis() + customMediaType.getExtension());
        return DataBufferUtils.write(dataBufferFlux, filePath, StandardOpenOption.CREATE)
                .thenReturn(filePath.toFile());
    }
}
