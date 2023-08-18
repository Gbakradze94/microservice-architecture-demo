package com.microservice.resourceservice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Resource {
    @Id
    @GeneratedValue(generator = "resource_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "resource_sequence", sequenceName = "resource_seq")
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    @Column(length = 180)
    private String path;

}
