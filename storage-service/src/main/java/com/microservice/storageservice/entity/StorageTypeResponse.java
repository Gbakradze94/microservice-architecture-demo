package com.microservice.storageservice.entity;

import lombok.Builder;



@Builder
public record StorageTypeResponse(Long id,
                                  StorageType storageType,
                                  String bucket,
                                  String path) {
}
