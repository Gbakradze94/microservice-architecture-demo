package com.microservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongMetaData {
    private Integer songId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private Integer resourceId;
    private String year;
}
