package com.microservice.resourceprocessor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSongDTO implements Serializable {
    private static final long serialVersionUID = 1234567890L;
    private long id;
    private long resourceId;
    private String name;
    private String artist;
    private String album;
    private String length;
    private int year;
}