package com.codetatva.task_logger.helper;

import com.codetatva.task_logger.dto.request.TaskCreateRequest;
import com.codetatva.task_logger.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskFactory {

    private static final TaskValidator validator = new TaskValidator();

    public static Task createTask(TaskCreateRequest request){
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(validator.validateDate(request.getDueDate()));
        task.setEstimatedHours(validator.validateHours(request.getEstimatedHours()));
        task.setPriority(validator.validatePriority(request.getPriority()));
        task.setAssignedTo(validator.validateUser(request.getAssignedTo()));
        return task;
    }
}
