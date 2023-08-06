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

}
