package com.microservice.resourceprocessor.kafka;

import com.microservice.resourceprocessor.config.KafkaConfiguration;
import com.microservice.resourceprocessor.config.TopicConfiguration;
import com.microservice.resourceprocessor.config.properties.TopicProperties;
import com.microservice.resourceprocessor.model.event.ResourceProcessedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(value = {SpringExtension.class, KafkaExtension.class})
@EnableConfigurationProperties(value = {TopicProperties.class, KafkaProperties.class,
        RetryProperties.class})
@ContextConfiguration(classes = {KafkaConfiguration.class, TopicConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
public class KafkaProducerTest {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ReactiveKafkaConsumerTemplate<String, ResourceProcessedEvent> resourceProcessedEventTemplate;

    @Test
    void shouldPublishEvent() {
        ResourceProcessedEvent event = new ResourceProcessedEvent(123L);
        Mono<SenderResult<Void>> senderResultMono = kafkaProducer.publish(event);

        StepVerifier.create(senderResultMono)
                .assertNext(voidSenderResult -> {
                    assertNotNull(voidSenderResult.recordMetadata());
                    assertTrue(voidSenderResult.recordMetadata().hasOffset());
                })
                .verifyComplete();
    }
}
