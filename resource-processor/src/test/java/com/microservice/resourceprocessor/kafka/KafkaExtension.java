package com.microservice.resourceprocessor.kafka;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class KafkaExtension implements BeforeAllCallback, AfterAllCallback {

    @Container
    private KafkaContainer container;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        container.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
        container.start();
        System.setProperty("KAFKA_BOOTSTRAP_SERVERS_ENDPOINTS", container.getBootstrapServers());
    }
}
