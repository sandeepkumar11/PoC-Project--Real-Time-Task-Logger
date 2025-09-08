package com.codetatva.task_logger.service.impl;

import com.codetatva.task_logger.dto.request.TaskCreateRequest;
import com.codetatva.task_logger.dto.request.TaskUpdateRequest;
import com.codetatva.task_logger.dto.response.TaskResponse;
import com.codetatva.task_logger.entity.Task;
import com.codetatva.task_logger.enums.EventType;
import com.codetatva.task_logger.events.TaskEvent;
import com.codetatva.task_logger.exception.ResourceNotFoundException;
import com.codetatva.task_logger.helper.TaskFactory;
import com.codetatva.task_logger.mapper.TaskMapper;
import com.codetatva.task_logger.repository.TaskRepository;
import com.codetatva.task_logger.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;
    private final TaskMapper mapper;

    @Autowired
    private KafkaTemplate<String, TaskEvent> kafkaTemplate;

    @Value("${kafka.topic.task-events:task-events}")
    private String taskEventsTopic;

    public TaskServiceImpl(TaskRepository taskRepository, TaskFactory taskFactory, TaskMapper mapper) {
        this.taskRepository = taskRepository;
        this.taskFactory = taskFactory;
        this.mapper = mapper;
    }

    @Override
    public TaskResponse create(TaskCreateRequest request) {
        Task task = taskFactory.createTask(request);

        if(request.getParentTaskId() != null && !request.getParentTaskId().isEmpty()) {
            Task parentTask = taskRepository.findById(UUID.fromString(request.getParentTaskId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Parent task not found"));
            task.setParentTask(parentTask);
            // Save task first to initialize ID and Version
            task = taskRepository.save(task);
            addSubtask(parentTask, task);
        }else {
            task = taskRepository.save(task);
        }

        // Publish event to Kafka
        publishTaskEvent(task, EventType.CREATED, task.getAssignedTo());

        // Record history - This can be implemented as needed
        // taskHistoryService.recordTaskCreation(savedTask, userId);

        return mapper.toResponse(task);
    }

    @Override
    public TaskResponse update(TaskUpdateRequest task) {
        return null;
    }

    @Override
    public TaskResponse getById(String id) {
        Task task = taskRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return mapper.toResponse(task);
    }

    @Override
    public void delete(String id) {

    }

    private void addSubtask(Task parentTask, Task subtask) {
        log.info("Adding subtask {} to parent task {}", subtask.getTitle(), parentTask.getTitle());
        parentTask.getSubTasks().add(subtask);
        subtask.setParentTask(parentTask);
        taskRepository.save(parentTask);

        // Publish event to Kafka
        publishTaskEvent(subtask, EventType.SUBTASK_ADDED, subtask.getAssignedTo());

        // Record history - This can be implemented as needed
        // taskHistoryService.recordSubtaskAddition(parentTask, subtask, userId);
    }

    private void publishTaskEvent(Task task, EventType eventType, String performedBy) {
        TaskEvent taskEvent = new TaskEvent(task, eventType, performedBy);
        kafkaTemplate.send(taskEventsTopic, task.getId().toString(), taskEvent);
        log.info("Published task event: {} for task ID: {}", eventType, task.getId());
    }
}
