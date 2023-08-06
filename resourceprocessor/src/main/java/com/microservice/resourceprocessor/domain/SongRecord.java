package com.microservice.resourceprocessor.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongRecord {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String length;
    private Integer resourceId;

    private String year;
    @Lob
    @Column(length = 10000)
    private byte[] data;
}
