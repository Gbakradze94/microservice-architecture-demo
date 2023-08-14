package com.microservice.resourceprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongMetaData {
    private Long id;
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private String year;
    private byte[] data;
}