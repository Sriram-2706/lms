package com.lms.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssignmentResponse {
    private Long id;
    private Long moduleId;
    private String title;
    private Integer maxScore;
    private LocalDateTime dueDate;
}
