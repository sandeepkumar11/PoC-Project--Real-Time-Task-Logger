package com.codetatva.task_logger.mapper;

import com.codetatva.task_logger.dto.response.TaskResponse;
import com.codetatva.task_logger.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }

        TaskResponse response = new TaskResponse();
        response.setId(task.getId().toString());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate() != null ? task.getDueDate().toString() : null);
        response.setEstimatedHours(task.getEstimatedHours() != null ? task.getEstimatedHours().toString() : null);
        response.setPriority(task.getPriority() != null ? task.getPriority().name() : null);
        response.setAssignedTo(task.getAssignedTo());
        response.setParentTaskId(task.getParentTask() != null ? task.getParentTask().getId().toString() : null);

        return response;
    }
}
