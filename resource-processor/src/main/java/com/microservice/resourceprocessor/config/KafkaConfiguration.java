package com.microservice.resourceprocessor.config;


import com.microservice.resourceprocessor.model.Pair;
import com.microservice.resourceprocessor.kafka.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;
import java.util.function.Function;

@Configuration
@EnableKafka
@RefreshScope
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


}
