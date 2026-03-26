package com.lms.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseModuleResponse {
    private Long id;
    private Long courseId;
    private String title;
    private Integer order;
}
