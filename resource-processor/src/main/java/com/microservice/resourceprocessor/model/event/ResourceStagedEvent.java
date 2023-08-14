package com.microservice.resourceprocessor.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceStagedEvent {
    private static final Long serialVersionUID = 1234567890L;
    private Long id;
}
