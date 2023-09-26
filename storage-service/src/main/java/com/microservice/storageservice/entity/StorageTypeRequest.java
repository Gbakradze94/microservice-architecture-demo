package com.microservice.storageservice.entity;

import com.microservice.storageservice.entity.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
public record StorageTypeRequest(StorageType storageType,
                                 String bucket,
                                 String path) {
}
