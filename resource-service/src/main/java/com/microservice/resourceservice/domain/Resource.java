package com.microservice.resourceservice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Resource {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
}
