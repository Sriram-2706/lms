package com.lms.lms.dto;

import lombok.Data;

@Data
public class CreateCourseRequest {
    private String title;
    private String description;
    private Long instructorId;
}
