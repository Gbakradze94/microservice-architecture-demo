package com.microservice.resourceprocessor.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

@Component
public class SongFileToDataBufferConverter implements Converter<Mono<File>, Flux<DataBuffer>> {
    @Override
    public Mono<File> convert(Flux<DataBuffer> input) {
        return FileProcessorHelper.writeDataBuffer(input, CustomMediaType.MP3);
    }
}
