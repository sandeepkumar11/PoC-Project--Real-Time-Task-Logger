package com.codetatva.task_logger.controller;

import com.codetatva.task_logger.dto.request.TaskCreateRequest;
import com.codetatva.task_logger.dto.response.TaskResponse;
import com.codetatva.task_logger.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request) {
        TaskResponse response = taskService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable String id) {
        TaskResponse response = taskService.getById(id);
        return ResponseEntity.ok(response);
    }
}
