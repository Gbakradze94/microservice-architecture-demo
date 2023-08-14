package com.microservice.resourceprocessor.service;

import reactor.core.publisher.Mono;

public interface ReactiveKafkaEventListener<E> {
    Mono<Void> listenToEvent(E event);
}
