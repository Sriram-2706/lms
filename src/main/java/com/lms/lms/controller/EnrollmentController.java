package com.lms.lms.controller;

import com.lms.lms.dto.EnrollRequest;
import com.lms.lms.entity.Enrollment;
import com.lms.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(@RequestBody EnrollRequest request) {
        Enrollment enrollment = enrollmentService.enroll(request.getUserId(), request.getCourseId());
        return ResponseEntity.ok("Enrolled: " + enrollment.getId());
    }
}
