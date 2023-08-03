package com.microservice.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Song {
    @Id
    @GeneratedValue
    private Integer songId;

    @Column
    private String name;

    @Column(length = 20)
    private String artist;

    @Column(length = 20)
    private String album;

    @Column(length = 20)
    private String length;

    private Integer resourceId;

    private String year;
}
