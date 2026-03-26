package com.lms.lms.dto;

import com.lms.lms.entity.ProgressStatus;
import lombok.Data;

@Data
public class ProgressRequest {
    private Long userId;
    private Long moduleId;
    private ProgressStatus status;
    private Integer score;
}
