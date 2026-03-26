package com.lms.lms.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AssignmentRequest {
    private String title;
    private Integer maxScore;
    private LocalDateTime dueDate;
}
