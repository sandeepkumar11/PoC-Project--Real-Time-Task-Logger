package com.codetatva.task_logger.helper;

import com.codetatva.task_logger.entity.Task;
import com.codetatva.task_logger.enums.EventType;
import com.codetatva.task_logger.events.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class TaskEventFactory {

    public static TaskEvent createTaskEvent(Task task, EventType eventType, String performedBy, String customDescription) {
        Objects.requireNonNull(task, "Task cannot be null");
        Objects.requireNonNull(eventType, "EventType cannot be null");
        Objects.requireNonNull(performedBy, "PerformedBy cannot be null");

        return TaskEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .eventType(eventType)
                .performedBy(performedBy)
                .description(customDescription != null ? customDescription : generateDescription(eventType, task))
                .taskId(task.getId() != null ? task.getId().toString() : null)
                .title(task.getTitle())
                .taskDescription(task.getDescription())
                .assignedTo(task.getAssignedTo())
                .priority(task.getPriority())
                .status(task.getStatus())
                .actualHours(task.getActualHours())
                .estimatedHours(task.getEstimatedHours())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .createdBy(task.getCreatedBy())
                .parentTaskId(task.getParentTask() != null ? task.getParentTask().getId().toString() : null)
                .version(task.getVersion())
                .build();
    }

    private static String generateDescription(EventType eventType, Task task) {
        String title = task.getTitle();
        return switch (eventType) {
            case CREATED -> String.format("Task '%s' was created", title);
            case UPDATED -> String.format("Task '%s' was updated", title);
            case DELETED -> String.format("Task '%s' was deleted", title);
            case STATUS_CHANGED -> String.format("Task '%s' status changed to %s", title, task.getStatus());
            case ASSIGNEE_CHANGED -> String.format("Task '%s' was assigned to user %s", title, task.getAssignedTo());
            case COMPLETED -> String.format("Task '%s' was completed", title);
            case COMMENT_ADDED -> String.format("Comment added to task '%s'", title);
            case SUBTASK_ADDED -> String.format("Subtask added to task '%s'", title);
            default -> String.format("Event occurred on task '%s'", title);
        };
    }
}
