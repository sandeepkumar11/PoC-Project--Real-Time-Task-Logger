package com.codetatva.task_logger.dto.request;

import lombok.Data;

@Data
public class TaskCreateRequest {
    private String title;
    private String description;
    private String dueDate;
    private String estimatedHours;
    private String priority;
    private String assignedTo;
    private String parentTaskId;
}
