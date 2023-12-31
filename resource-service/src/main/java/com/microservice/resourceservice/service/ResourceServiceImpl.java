package com.microservice.resourceservice.service;

import com.microservice.resourceservice.domain.Resource;
import com.microservice.resourceservice.domain.ResourceResponse;
import com.microservice.resourceservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    @Value("${song-service.path}")
    private String songServicePath;
    private final ResourceRepository resourceRepository;
    private final S3StorageRepository s3StorageRepository;


    public ResourceResponse saveResource(MultipartFile multipartFile) throws IOException {
        log.info("Saving file '{}'", multipartFile.getOriginalFilename());
        String filePath = s3StorageRepository.upload(multipartFile);
        Resource resource = Resource.builder()
                .path(filePath)
                .createdAt(LocalDateTime.now())
                .name(multipartFile.getOriginalFilename())
                .build();
        resourceRepository.save(resource);

        return ResourceResponse.builder()
                .id(resource.getId())
                .resourceUrl(filePath)
                .build();
    }

    public Resource getResource(Long id) {
        return resourceRepository.findById(Math.toIntExact(id))
                .map(songRecord -> Resource.builder().id(songRecord.getId()).build())
                .orElseThrow(() -> new RuntimeException("Resource with this id could not be found"));
    }

    public List<ResourceResponse> deleteByIds(int[] ids) {
        Arrays.stream(ids)
                .forEach(resourceRepository::deleteById);

        return Arrays.stream(ids)
                .mapToObj(i -> Integer.parseInt(String.valueOf(i)))
                .map(i -> ResourceResponse.builder().id(i).build())
                .collect(Collectors.toList());
    }
    public String downloadFile(String id) {
        return s3StorageRepository.download(id);
    }
}
