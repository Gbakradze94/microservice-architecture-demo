package com.microservice.resourceprocessor.kafka;

import com.microservice.resourceprocessor.model.Pair;
import com.microservice.resourceprocessor.model.event.ResourceStagedEvent;
import com.microservice.resourceprocessor.model.exception.ReceiverRecordException;
import com.microservice.resourceprocessor.service.ReactiveKafkaEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.function.Function;

@Slf4j
public class KafkaConsumer {
    private final DeadLetterPublishingRecoverer deadLetterPublishingRecoverer;
    private final RetryProperties retryProperties;
    private final Pair<ReactiveKafkaConsumerTemplate<String, ResourceStagedEvent>,
            ReactiveKafkaEventListener<ResourceStagedEvent>> resourceStagedEventListener;

    public KafkaConsumer(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer,
                         RetryProperties retryProperties,
                         Pair<ReactiveKafkaConsumerTemplate<String, ResourceStagedEvent>, ReactiveKafkaEventListener<ResourceStagedEvent>>
                                 resourceStagedEventListener) {
        this.deadLetterPublishingRecoverer = deadLetterPublishingRecoverer;
        this.retryProperties = retryProperties;
        this.resourceStagedEventListener = resourceStagedEventListener;
    }

    @EventListener
    public void subscribe() {
        listenToEvent(resourceStagedEventListener.getSource(), resourceStagedEventListener.getTarget());
    }

    private <T> void listenToEvent(ReactiveKafkaConsumerTemplate<String, T> consumerTemplate, ReactiveKafkaEventListener<T> eventListener) {
        listenWithHandler(consumerTemplate, f -> handler(eventListener, f));
    }

    private <T> void listenWithHandler(ReactiveKafkaConsumerTemplate<String, T> consumerTemplate,
                                       Function<ReceiverRecord<String, T>, Mono<?>> handler) {
consumerTemplate.receive()
        .doOnNext(receiverRecord -> log.info("Received event message with key {}, value {}", receiverRecord.key(),
                receiverRecord.value()))
        .flatMap(receiverRecord -> handler.apply(receiverRecord)
                .doOnSuccess(r -> acknowledge(receiverRecord))
                .doOnError(e -> log.error("Exception occurred in Listener", e))
                .onErrorMap(error -> new ReceiverRecordException(receiverRecord, error))
                .retryWhen(Retry.backoff(retryProperties.getMaxAttempts(), Duration.ofMillis(retryProperties.getInitialInterval()))
                        .transientErrors(true))
                .onErrorResume(error -> sendToDeadLockQueue((ReceiverRecordException) error.getCause())))
        .repeat()
        .subscribe();
    }

    private void acknowledge(ReceiverRecord<?, ?> receiverRecord) {
        receiverRecord.receiverOffset().acknowledge();
    }

    private <T> Mono<T> sendToDeadLockQueue(ReceiverRecordException exception) {
        log.info("Exception occurred: {}, sending event '{}' to dead lock queue", exception, exception.getReceiverRecord());
        deadLetterPublishingRecoverer.accept(exception.getReceiverRecord(), exception);
        acknowledge(exception.getReceiverRecord());
        return Mono.empty();
    }

    private <T> Mono<?> handler(ReactiveKafkaEventListener<T> eventListener, ReceiverRecord<String, T> receiverRecord) {
        return eventListener.listenToEvent(receiverRecord.value());
    }
}
