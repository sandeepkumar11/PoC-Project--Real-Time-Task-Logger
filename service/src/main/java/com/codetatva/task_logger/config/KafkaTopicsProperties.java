package com.codetatva.task_logger.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicsProperties {

    private Map<String, TopicConfig> topics;

    @Data
    public static class TopicConfig {
        private int partitions;
        private int replicas;
        private long retentionMs;
    }
}
