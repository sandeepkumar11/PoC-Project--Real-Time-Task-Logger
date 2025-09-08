package com.codetatva.task_logger.service;

import com.codetatva.task_logger.dto.request.TaskCreateRequest;
import com.codetatva.task_logger.dto.request.TaskUpdateRequest;
import com.codetatva.task_logger.dto.response.TaskResponse;

public interface TaskService {
    TaskResponse create(TaskCreateRequest task);

    TaskResponse update(TaskUpdateRequest task);

    TaskResponse getById(String id);

    void delete(String id);
}
