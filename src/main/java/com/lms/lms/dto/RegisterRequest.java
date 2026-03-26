package com.lms.lms.dto;

import com.lms.lms.entity.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private UserRole role;
}
