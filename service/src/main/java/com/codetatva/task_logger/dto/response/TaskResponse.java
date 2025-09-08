package com.codetatva.task_logger.dto.response;

import lombok.Data;

@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String estimatedHours;
    private String priority;
    private String assignedTo;
    private String parentTaskId;
}
