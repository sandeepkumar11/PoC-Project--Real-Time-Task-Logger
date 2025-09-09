package com.codetatva.task_logger.events;

import com.codetatva.task_logger.enums.EventType;
import com.codetatva.task_logger.enums.TaskPriority;
import com.codetatva.task_logger.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@Jacksonized // For deserialization with @Builder
public class TaskEvent {

    private final String eventId;

    @JsonProperty("event_type")
    private final EventType eventType;
    private final String performedBy;

    /**
     * Description of the event.
     */
    private final String description;

    // Task details snapshot
    private final String taskId;
    private final String title;
    private final String taskDescription;
    private final String assignedTo;
    private final TaskPriority priority;
    private final TaskStatus status;
    private final BigDecimal actualHours;
    private final BigDecimal estimatedHours;
    private final LocalDate dueDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String parentTaskId;
    private final Integer version;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

}
