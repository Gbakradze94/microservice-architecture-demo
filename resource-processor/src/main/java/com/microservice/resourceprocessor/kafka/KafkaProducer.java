package com.microservice.resourceprocessor.kafka;

import com.microservice.resourceprocessor.model.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.util.Map;
import java.util.function.Function;

@Slf4j
public class KafkaProducer {

    private final ReactiveKafkaProducerTemplate<String, Object> kafkaProducerTemplate;

    private final Map<Class<?>, Pair<String, Function<Object, ProducerRecord<String, Object>>>> topics;

    public KafkaProducer(ReactiveKafkaProducerTemplate<String, Object> kafkaProducerTemplate,
                         Map<Class<?>, Pair<String, Function<Object, ProducerRecord<String, Object>>>> topics){
        this.kafkaProducerTemplate = kafkaProducerTemplate;
        this.topics = topics;
    }
    public Mono<SenderResult<Void>> publish(Object message) {
        if(topics.containsKey(message.getClass())) {
            log.info("Publishing message: {} to kafka: {}", message.getClass().getName(), message);
            return kafkaProducerTemplate.send(topics.get(message.getClass()).getTarget().apply(message));
        }
        return Mono.error(new IllegalStateException("There is no kafka topic for this message type: " + message.getClass()));
    }
}
