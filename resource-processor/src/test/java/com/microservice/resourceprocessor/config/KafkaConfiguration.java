package com.microservice.resourceprocessor.config;


import com.microservice.resourceprocessor.config.properties.TopicProperties;
import com.microservice.resourceprocessor.kafka.KafkaProducer;
import com.microservice.resourceprocessor.model.Pair;
import com.microservice.resourceprocessor.model.event.ResourceProcessedEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Configuration
@EnableKafka
public class KafkaConfiguration {
    @Bean
    public ReactiveKafkaProducerTemplate<String, Object> kafkaProducerTemplate(KafkaProperties properties) {
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(properties.buildProducerProperties()));
    }

    @Bean
    public KafkaProducer kafkaProducer(ReactiveKafkaProducerTemplate<String, Object> kafkaProducerTemplate,
                                       Map<Class<?>, Pair<String, Function<Object, ProducerRecord<String, Object>>>> topics) {
        return new KafkaProducer(kafkaProducerTemplate, topics);
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ResourceProcessedEvent> resourceProcessedEventConsumer(KafkaProperties kafkaProperties,
                                                                                                        TopicProperties topicProperties) {
        ReceiverOptions<String, ResourceProcessedEvent> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        ReceiverOptions<String, ResourceProcessedEvent> receiverOptions =
                basicReceiverOptions.subscription(Collections.singletonList(topicProperties.getResourcePermanent()));
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }
}
