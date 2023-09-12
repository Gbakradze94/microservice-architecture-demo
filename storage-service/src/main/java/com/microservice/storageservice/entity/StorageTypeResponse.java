package com.microservice.storageservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public record StorageTypeResponse(Long id,
                                  StorageType storageType,
                                  String bucket,
                                  String path) {
}
