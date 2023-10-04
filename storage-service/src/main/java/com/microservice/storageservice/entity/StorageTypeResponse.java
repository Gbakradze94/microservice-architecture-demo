package com.microservice.storageservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class StorageTypeResponse {
    private Long id;
    private StorageType storageType;
    private String bucket;
    private String path;
}
