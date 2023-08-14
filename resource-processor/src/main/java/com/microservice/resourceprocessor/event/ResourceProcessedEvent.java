package com.microservice.resourceprocessor.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceProcessedEvent {
    private static final Long serialVersionUID = 1234567890L;
    private Long id;
}
