package com.lms.lms.dto;

import com.lms.lms.entity.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProgressResponse {
    private Long id;
    private Long userId;
    private Long moduleId;
    private ProgressStatus status;
    private Integer score;
    private LocalDateTime completedAt;
}
