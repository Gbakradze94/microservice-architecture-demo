package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.domain.ResourceRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceProcessorService {

    private final ResourceServiceClient resourceServiceClient;
    public boolean processResource(ResourceRecord resourceRecord) {
        log.info("Processing resourceRecord: {}", resourceRecord);

//        Optional<File> fileOptional = resourceServiceClient.getById(resourceRecord.getId());
//        boolean isProcessed = fileOptional.map(file -> {
//                    try {
//                        return processFile(resourceRecord.getId(), file);
//                    } catch (InvalidDataException | UnsupportedTagException | IOException ex) {
//                        log.error("MP3 file processing failed for resource '{}' ", resourceRecord, ex);
//                        return null;
//                    }
//                })
//                .map(songServiceClient::post)
//                .isPresent();
//
//        fileOptional.ifPresent(FileUtils::delete);
        return false;
    }

}
