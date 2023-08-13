package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.domain.ResourceRecord;
import com.microservice.resourceprocessor.domain.context.ResourceProcessingContext;
import com.microservice.resourceprocessor.domain.dto.SaveSongDTO;
import com.microservice.resourceprocessor.util.Converter;
import com.microservice.resourceprocessor.util.FileProcessorHelper;
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
    private final FileProcessorHelper fileProcessorHelper;

    private final ResourceProcessingContext context;
    public Mono<ResourceProcessingContext> processResource(ResourceRecord resourceRecord) throws TikaException, IOException, SAXException {
        return converter.convert(resourceServiceClient.getById(resourceRecord.getId()))
                .map(context::withResourceFile)
                .flatMap(this::processFile);

    }

    private Mono<ResourceProcessingContext> processFile(ResourceProcessingContext context) {
        try {
            Mono<ResourceProcessingContext> resourceProcessingContext = extractSongMetadata(context.getResourceId(), context.getResourceFile());
            return resourceProcessingContext;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<ResourceProcessingContext> extractSongMetadata(Long id, File songFile) throws IOException, TikaException, SAXException, TikaException {

        Path filePath = Path.of(BASE_FILE_DIRECTORY_PATH);
        FileInputStream inputstream = new FileInputStream(filePath + "\\" + songFile.getName());
        log.info("PATH: " + inputstream);
        log.info("ID: " + id);
        mp3Parser.parse(inputstream, bodyContentHandler, metadata, parseContext);
        return Mono.just(context.withSaveSongDTO(SaveSongDTO.builder()
                .id(id)
                .name(metadata.get("dc:title"))
                .resourceId(new Random().nextInt())
                .artist(metadata.get("xmpDM:albumArtist"))
                .album(metadata.get("xmpDM:album"))
                .length(metadata.get("xmpDM:duration"))
                .year(metadata.get("xmpDM:releaseDate"))
                .data(Files.readAllBytes(Path.of(songFile.getAbsolutePath())))
                .build()
            )
        );
    }
}
