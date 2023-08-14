package com.microservice.resourceprocessor.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(prefix = TopicProperties.PREFIX)
public class TopicProperties {
    public static final String PREFIX = "kafka.topic";
    private String resourceStaging;
    private String resourcePermanent;

    private final Properties properties = new Properties();

    public String getResourceStaging() {
        return resourceStaging;
    }

    public void setResourceStaging(String resourceStaging) {
        this.resourceStaging = resourceStaging;
    }

    public String getResourcePermanent() {
        return resourcePermanent;
    }

    public void setResourcePermanent(String resourcePermanent) {
        this.resourcePermanent = resourcePermanent;
    }

    public Properties getProperties() {
        return properties;
    }

    public static class Properties {
        private int partitionCount;
        private int replicationFactor;

        public int getPartitionCount() {
            return partitionCount;
        }

        public void setPartitionCount(int partitionCount) {
            this.partitionCount = partitionCount;
        }

        public int getReplicationFactor() {
            return replicationFactor;
        }

        public void setReplicationFactor(int replicationFactor) {
            this.replicationFactor = replicationFactor;
        }
    }
}
