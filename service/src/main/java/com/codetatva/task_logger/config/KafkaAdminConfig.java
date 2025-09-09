package com.codetatva.task_logger.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafka
public class KafkaAdminConfig {

    @Autowired
    private KafkaTopicsProperties kafkaTopicsProperties;

    @Bean
    public List<NewTopic> kafkaTopics() {
        List<NewTopic> topics = new ArrayList<>();
        Map<String, KafkaTopicsProperties.TopicConfig> topicConfigMap = kafkaTopicsProperties.getTopics();
        if (topicConfigMap == null || topicConfigMap.isEmpty()) {
            log.error("No Kafka topics configured in application properties.");
            return topics; // Return empty list if no topics are configured
        }
        for (Map.Entry<String, KafkaTopicsProperties.TopicConfig> entry : topicConfigMap.entrySet()) {
            String name = entry.getKey();
            KafkaTopicsProperties.TopicConfig config = entry.getValue();
            NewTopic topic = new NewTopic(name, config.getPartitions(), (short) config.getReplicas())
                    .configs(Map.of("retention.ms", String.valueOf(config.getRetentionMs())));
            topics.add(topic);
        }
        log.info("Configured Kafka topics: {}", topics);
        return topics;
    }


}
