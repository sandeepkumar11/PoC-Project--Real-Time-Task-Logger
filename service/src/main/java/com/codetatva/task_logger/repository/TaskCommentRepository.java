package com.codetatva.task_logger.repository;

import com.codetatva.task_logger.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskCommentRepository extends JpaRepository<TaskComment,UUID> {
}
