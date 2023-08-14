package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.model.SongMetaData;
import com.microservice.resourceprocessor.model.context.ResourceProcessingContext;
import com.microservice.resourceprocessor.event.ResourceProcessedEvent;
import com.microservice.resourceprocessor.event.ResourceStagedEvent;
import com.microservice.resourceprocessor.kafka.KafkaProducer;
import com.microservice.resourceprocessor.util.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceProcessorService {
    public static final String BASE_FILE_DIRECTORY_PATH = "C:\\Users\\Giorgi_Bakradze\\IdeaProjects\\microservice-architecture-demo\\resourceprocessor\\src\\main\\resources\\files";
    private final Mp3Parser mp3Parser;
    private final Metadata metadata;
    private final ParseContext parseContext;
    private final BodyContentHandler bodyContentHandler;
    private final ResourceServiceClient resourceServiceClient;
    private final SongServiceClient songServiceClient;
    private final Converter<Mono<File>, Flux<DataBuffer>> converter;
    private final KafkaProducer kafkaProducer;

    public Mono<Void> processResource(final ResourceStagedEvent resourceStagedEvent) {
        log.info("Processing the resource with id '{}'", resourceStagedEvent.getId());
        ResourceProcessingContext context = new ResourceProcessingContext().withResourceId(resourceStagedEvent.getId());
        return converter.convert(resourceServiceClient.getById(context.getResourceId()))
                .map(context::withResourceFile)
                .flatMap(this::processFile)
                .flatMap(this::publishResourceProcessedEvent);

    }

    private Mono<Void> publishResourceProcessedEvent(ResourceProcessingContext context) {
        return kafkaProducer.publish(new ResourceProcessedEvent(context.getResourceId())).then();
    }

    private Mono<ResourceProcessingContext> postSongMetadata(final ResourceProcessingContext context) {
        return songServiceClient.post(context.getSongMetaData()).map(context::withGetSongDTO);
    }


    private Mono<ResourceProcessingContext> processFile(ResourceProcessingContext context) {
        try {
            Mono<ResourceProcessingContext> resourceProcessingContext = extractSongMetadata(context);
            return resourceProcessingContext;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<ResourceProcessingContext> extractSongMetadata(final ResourceProcessingContext context) throws IOException, TikaException, SAXException, TikaException {

        Path filePath = Path.of(BASE_FILE_DIRECTORY_PATH);
        FileInputStream inputstream = new FileInputStream(filePath + "\\" + context.getResourceFile().getName());
        log.info("PATH: " + inputstream);
        log.info("ID: " + context.getResourceId());
        mp3Parser.parse(inputstream, bodyContentHandler, metadata, parseContext);
        return Mono.just(context.withSongMetaData(SongMetaData.builder()
                        .id(context.getResourceId())
                        .name(metadata.get("dc:title"))
                        .resourceId(new Random().nextInt())
                        .artist(metadata.get("xmpDM:albumArtist"))
                        .album(metadata.get("xmpDM:album"))
                        .length(metadata.get("xmpDM:duration"))
                        .year(metadata.get("xmpDM:releaseDate"))
                        .data(Files.readAllBytes(Path.of(context.getResourceFile().getAbsolutePath())))
                        .build()
                )
        );
    }


}
