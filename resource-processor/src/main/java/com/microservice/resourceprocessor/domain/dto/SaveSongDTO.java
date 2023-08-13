package com.microservice.resourceprocessor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveSongDTO implements Serializable {
    private static final long serialVersionUID = 1234567890L;
    private Long id;
    private Integer resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private String year;
    private byte[] data;
}