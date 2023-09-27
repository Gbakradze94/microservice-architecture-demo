package com.microservice.storageservice.entity;

import com.microservice.storageservice.entity.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageTypeRequest {
   private StorageType storageType;
   private String bucket;
   private String path;
}
