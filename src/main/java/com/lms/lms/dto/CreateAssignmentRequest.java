package com.lms.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAssignmentRequest {
    private String title;
    private Integer maxScore;
    private LocalDateTime dueDate;
}
