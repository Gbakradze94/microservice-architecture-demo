package com.microservice.resourceservice.service;

import com.microservice.resourceservice.domain.Resource;
import com.microservice.resourceservice.domain.ResourceResponse;
import com.microservice.resourceservice.domain.SongRecord;
import com.microservice.resourceservice.domain.SongRecordId;
import com.microservice.resourceservice.repository.ResourceRepository;
import com.microservice.resourceservice.repository.SongRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.SAXException;
import reactor.core.publisher.Mono;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {

    public static final String BASE_MP3_FILE_PATH = "C:\\Users\\Giorgi_Bakradze\\IdeaProjects\\microservices-architecture-overview\\resource-service\\src\\main\\resources\\files\\";

    @Value("${song-service.path}")
    private String songServicePath;
    private final ResourceRepository resourceRepository;
    private final SongRecordRepository songRecordRepository;
    private final BodyContentHandler bodyContentHandler;
    private final Metadata metadata;
    private final WebClient webClient;

    public ResourceResponse saveResource(MultipartFile multipartFile) throws IOException, TikaException, SAXException {


        SongRecord songRecord = extractSongRecordFromMetadata(multipartFile);

        songRecordRepository.save(songRecord);
        log.info("RESOURCEID: " + songRecord.getSongId());
        Mono<SongRecordId> songRecordId = webClient
                .method(HttpMethod.POST)
                .uri(songServicePath)
                .body(Mono.just(songRecord), SongRecord.class)
                .retrieve()
                .bodyToMono(SongRecordId.class);

        return ResourceResponse.builder()
                .id(songRecordId.block().getId())
                .build();
    }

    public Resource getResource(Long id) {
        return resourceRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Resource with this id could not be found"));
    }

    private SongRecord extractSongRecordFromMetadata(MultipartFile multipartFile) throws IOException, TikaException, SAXException {
        File songFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileInputStream inputstream = new FileInputStream(BASE_MP3_FILE_PATH + songFile.getPath());
        ParseContext pcontext = new ParseContext();
        Mp3Parser Mp3Parser = new Mp3Parser();
        Mp3Parser.parse(inputstream, bodyContentHandler, metadata, pcontext);
        return SongRecord.builder()
                .name(metadata.get("dc:title"))
                .resourceId(new Random().nextInt())
                .artist(metadata.get("xmpDM:albumArtist"))
                .album(metadata.get("xmpDM:album"))
                .length(metadata.get("xmpDM:duration"))
                .year(metadata.get("xmpDM:releaseDate"))
                .data(multipartFile.getBytes())
                .build();
    }

    public List<ResourceResponse> deleteByIds(int[] ids) {
        Arrays.stream(ids)
                .forEach(resourceRepository::deleteById);

        return Arrays.stream(ids)
                .mapToObj(i -> Integer.parseInt(String.valueOf(i)))
                .map(ResourceResponse::new)
                .collect(Collectors.toList());
    }
}
