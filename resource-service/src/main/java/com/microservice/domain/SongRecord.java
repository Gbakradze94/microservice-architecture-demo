package com.microservice.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SongRecord {
    @Id
    @GeneratedValue
    private Integer songId;
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
