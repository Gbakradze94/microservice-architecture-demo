package com.microservice.storageservice.controller;

import com.microservice.storageservice.entity.Storage;
import com.microservice.storageservice.entity.StorageTypeRequest;
import com.microservice.storageservice.entity.StorageTypeResponse;
import com.microservice.storageservice.repository.StorageRepository;
import com.microservice.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    private final StorageRepository storageRepository;

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> createStorageType(@RequestBody StorageTypeRequest storageTypeRequest) {
        Storage storage = Storage.builder()
                .storageType(storageTypeRequest.getStorageType())
                .bucket(storageTypeRequest.getBucket())
                .path(storageTypeRequest.getPath())
                .build();

        return new HttpEntity<>(storageRepository.save(storage).getId());
    }

    @PreAuthorize(value = "hasAuthority('USER')")
    public HttpEntity<List<StorageTypeResponse>> getStorageTypes() {
        return new HttpEntity<>(storageRepository.findAll().stream()
                .map(storage -> StorageTypeResponse.builder()
                        .id(storage.getId())
                        .storageType(storage.getStorageType())
                        .bucket(storage.getBucket())
                        .path(storage.getPath())
                        .build()
                )
                .collect(Collectors.toList()));
    }
}
