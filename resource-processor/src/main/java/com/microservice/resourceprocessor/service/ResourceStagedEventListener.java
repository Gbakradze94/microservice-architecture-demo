package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.event.ResourceStagedEvent;
import reactor.core.publisher.Mono;

public class ResourceStagedEventListener implements ReactiveKafkaEventListener<ResourceStagedEvent> {

    private final ResourceProcessorService resourceProcessorService;

    public ResourceStagedEventListener(ResourceProcessorService resourceProcessorService) {
        this.resourceProcessorService = resourceProcessorService;
    }

    @Override
    public Mono<Void> listenToEvent(ResourceStagedEvent event) {
       return resourceProcessorService.processResource(event);
    }
}
