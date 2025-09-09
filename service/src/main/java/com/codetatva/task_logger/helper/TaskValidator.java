package com.codetatva.task_logger.helper;

import com.codetatva.task_logger.enums.TaskPriority;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TaskValidator {

    public LocalDate validateDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }

    public BigDecimal validateHours(String hoursStr) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(hoursStr));
        } catch (Exception e) {
            throw new IllegalArgumentException("Estimated hours must be a valid number.");
        }
    }

    public TaskPriority validatePriority(String priorityStr) {
        try {
            return TaskPriority.valueOf(priorityStr.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Priority must be a valid enum.");
        }
    }

    public String validateUser(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        // Additional validation logic can be added here (e.g., check if user exists in auth-service)
        return userId;
    }

}
