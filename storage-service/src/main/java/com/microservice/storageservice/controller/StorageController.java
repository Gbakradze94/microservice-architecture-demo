package com.microservice.storageservice.controller;

import com.microservice.entity.StorageTypeRequest;
import com.microservice.storageservice.entity.Storage;
import com.microservice.storageservice.entity.StorageType;
import com.microservice.storageservice.entity.StorageTypeResponse;
import com.microservice.storageservice.repository.StorageRepository;
import com.microservice.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    private final StorageRepository storageRepository;

    public HttpEntity<Long> createStorageType(@RequestBody StorageTypeRequest storageTypeRequest) {
        Storage storage = Storage.builder()
                .storageType(storageTypeRequest.storageType())
                .bucket(storageTypeRequest.bucket())
                .path(storageTypeRequest.path())
                .build();

        return new HttpEntity<>(storageRepository.save(storage).getId());
    }

    public HttpEntity<List<StorageTypeResponse>> getStorageTypes() {
        return new HttpEntity<>(storageRepository.findAll().stream()
                .map(storage -> StorageTypeResponse.builder()
                        .id(storage.getId())
                        .storageType(storage.getStorageType())
                        .bucket(storage.getBucket())
                        .path(storage.getPath())
                        .build()
                )
                .toList());
    }
}
