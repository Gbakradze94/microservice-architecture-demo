package com.microservice.resourceprocessor.util;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public final class FileProcessorHelper {
    private static final String DIRECTORY = "src/main/resources";
    private static final String TEMP_DIRECTORY = "temp";

    private FileProcessorHelper() {
    }

    public static Mono<File> writeDataBuffer(Flux<DataBuffer> dataBufferFlux, CustomMediaType customMediaType) {
        Path tempDirectoryPath = Paths.get(DIRECTORY, TEMP_DIRECTORY);
        if (!Files.exists(tempDirectoryPath)) {
            makeTempDirectory(tempDirectoryPath);
        }
        Path path = tempDirectoryPath.resolve(System.currentTimeMillis() + customMediaType.getExtension());
        return DataBufferUtils.write(dataBufferFlux, path, StandardOpenOption.CREATE)
                .thenReturn(path.toFile());
    }

    private static void makeTempDirectory(Path tempDirectoryPath) {
        try {
            log.info("Creating temp directory path '{}'", tempDirectoryPath);
            Files.createDirectories(tempDirectoryPath).toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
