package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.domain.ResourceRecord;
import com.microservice.resourceprocessor.domain.SongMetaData;
import com.microservice.resourceprocessor.domain.SongRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResourceProcessorService {
    public static final String BASE_FILE_DIRECTORY_PATH = "C:\\Users\\Giorgi_Bakradze\\IdeaProjects\\microservice-architecture-demo\\resourceprocessor\\src\\main\\resources\\files";
    private final Mp3Parser mp3Parser;
    private final Metadata metadata;
    private final ParseContext parseContext;
    private final BodyContentHandler bodyContentHandler;
    private final ResourceServiceClient resourceServiceClient;
    private final SongServiceClient songServiceClient;


    public boolean processResource(ResourceRecord resourceRecord) throws TikaException, IOException, SAXException {
        Optional<File> songFile = resourceServiceClient.getById(resourceRecord.getId());
        return songFile.map(file -> {;
                    try {
                        log.info("resourceRecord.getId(): " + resourceRecord.getId());
                        return extractSongRecordFromMetadata(resourceRecord.getId(), file);
                    } catch (TikaException | SAXException | IOException e) {
                        log.error("MP3 file processing failure for: '{}' ", file.getAbsoluteFile().getName(), e);
                        return null;
                    }
                })
                .map(songServiceClient::post)
                .isPresent();
    }

    private SongMetaData extractSongRecordFromMetadata(Long id, File songFile) throws IOException, TikaException, SAXException, TikaException {

        Path filePath = Path.of(BASE_FILE_DIRECTORY_PATH);
        FileInputStream inputstream = new FileInputStream(filePath + "\\" + songFile.getName());
        log.info("PATH: " + inputstream);
        log.info("ID: " + id);
        mp3Parser.parse(inputstream, bodyContentHandler, metadata, parseContext);
        return SongMetaData.builder()
                .id(id)
                .name(metadata.get("dc:title"))
                .resourceId(new Random().nextInt())
                .artist(metadata.get("xmpDM:albumArtist"))
                .album(metadata.get("xmpDM:album"))
                .length(metadata.get("xmpDM:duration"))
                .year(metadata.get("xmpDM:releaseDate"))
                .data(Files.readAllBytes(Path.of(songFile.getAbsolutePath())))
                .build();
    }
}
