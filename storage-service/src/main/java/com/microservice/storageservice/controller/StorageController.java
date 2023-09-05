package com.microservice.storageservice.controller;

import com.microservice.entity.StorageTypeRequest;
import com.microservice.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    public HttpEntity<Long> createStorageType(@RequestBody StorageTypeRequest storageTypeRequest) {
        return null;
    }
}
