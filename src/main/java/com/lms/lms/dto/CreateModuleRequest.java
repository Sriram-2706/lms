package com.lms.lms.dto;

import lombok.Data;

@Data
public class CreateModuleRequest {
    private String title;
    private Integer order;
}
