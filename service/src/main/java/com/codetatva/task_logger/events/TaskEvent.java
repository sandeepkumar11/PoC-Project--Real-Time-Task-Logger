package com.codetatva.task_logger.events;

import com.codetatva.task_logger.entity.Task;
import com.codetatva.task_logger.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TaskEvent {
    //TODO: Only include fields that are necessary for the event, not the entire Task object
    private final String eventId;
    private EventType eventType;
    private Task task;
    private String performedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private String description;

    public TaskEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

    public TaskEvent(Task task, EventType eventType, String performedBy) {
        this();
        this.task = task;
        this.eventType = eventType;
        this.performedBy = performedBy;
        this.description = generateDescription();
    }

    public TaskEvent(Task task, EventType eventType, String performedBy, String customDescription) {
        this();
        this.task = task;
        this.eventType = eventType;
        this.performedBy = performedBy;
        this.description = customDescription;
    }

    private String generateDescription() {
        return switch (eventType) {
            case CREATED -> String.format("Task '%s' was created", task.getTitle());
            case UPDATED -> String.format("Task '%s' was updated", task.getTitle());
            case DELETED -> String.format("Task '%s' was deleted", task.getTitle());
            case STATUS_CHANGED -> String.format("Task '%s' status changed to %s", task.getTitle(), task.getStatus());
            case ASSIGNEE_CHANGED ->
                    String.format("Task '%s' was assigned to user %s", task.getTitle(), task.getAssignedTo());
            case COMPLETED -> String.format("Task '%s' was completed", task.getTitle());
            case COMMENT_ADDED -> String.format("Comment added to task '%s'", task.getTitle());
            case SUBTASK_ADDED -> String.format("Subtask added to task '%s'", task.getTitle());
            default -> String.format("Event occurred on task '%s'", task.getTitle());
        };
    }

    @Override
    public String toString() {
        return "TaskEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventType=" + eventType +
                ", taskId=" + (task != null ? task.getId() : null) +
                ", performedBy='" + performedBy + '\'' +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }

}
