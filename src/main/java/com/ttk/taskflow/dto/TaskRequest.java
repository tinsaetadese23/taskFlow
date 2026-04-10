package com.ttk.taskflow.dto;

import java.time.LocalDateTime;

public record TaskRequest(
        String title,
        String description,
        String status,    // We'll map these strings to Enums in the service
        String priority,
        Long categoryId,
        LocalDateTime dueDate
) {}