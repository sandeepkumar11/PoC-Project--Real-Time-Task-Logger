package com.codetatva.task_logger.consumer;

import com.codetatva.task_logger.events.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskEventConsumer {

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    @Value("${kafka.topics.task-events:task-events}")
    private String taskEventsTopic;

    @Value("${spring.kafka.consumer.group-id:task-logger-group}")
    private String groupId;

    @KafkaListener(topics = "task-events", groupId = "task-logger-group")
    public void consumeTaskEvent(TaskEvent event){
        // Send real-time update to subscribed clients
        log.info("Received Task Event: {}", event);
        String destination = "/topic/task-updates/" + event.getAssignedTo();
//        messagingTemplate.convertAndSend(destination, event);
    }
}
