package com.lms.lms.controller;

import com.lms.lms.dto.AuthResponse;
import com.lms.lms.dto.LoginRequest;
import com.lms.lms.dto.RegisterRequest;
import com.lms.lms.entity.UserRole;
import com.lms.lms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/student")
    public ResponseEntity<AuthResponse> registerStudent(@RequestBody RegisterRequest request) {
        request.setRole(UserRole.STUDENT);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/register/instructor")
    public ResponseEntity<AuthResponse> registerInstructor(@RequestBody RegisterRequest request) {
        request.setRole(UserRole.INSTRUCTOR);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
