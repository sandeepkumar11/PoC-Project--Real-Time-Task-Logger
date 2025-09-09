package com.codetatva.task_logger.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaTopicsProperties {

    private Map<String, TopicConfig> topics;

    @Data
    public static class TopicConfig {
        private int partitions;
        private int replicas;
        private long retentionMs;
    }
}
