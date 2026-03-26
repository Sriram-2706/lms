package com.lms.lms.controller;

import com.lms.lms.dto.ProgressRequest;
import com.lms.lms.dto.ProgressResponse;
import com.lms.lms.entity.Progress;
import com.lms.lms.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/progress")
    public ResponseEntity<ProgressResponse> createOrUpdateProgress(@RequestBody ProgressRequest request) {
        Progress progress = progressService.createOrUpdateProgress(request.getUserId(), request.getModuleId(), request.getStatus(), request.getScore());
        return ResponseEntity.ok(ProgressService.toDto(progress));
    }

    @GetMapping("/users/{userId}/progress")
    public ResponseEntity<List<ProgressResponse>> getProgress(@PathVariable Long userId) {
        List<ProgressResponse> items = progressService.getProgressForUser(userId).stream()
                .map(ProgressService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }
}
