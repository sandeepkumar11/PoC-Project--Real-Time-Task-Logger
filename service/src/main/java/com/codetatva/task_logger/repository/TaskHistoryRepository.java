package com.codetatva.task_logger.repository;

import com.codetatva.task_logger.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, UUID> {
}
