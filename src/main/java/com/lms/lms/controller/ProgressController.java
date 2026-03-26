package com.lms.lms.controller;

import com.lms.lms.entity.Progress;
import com.lms.lms.entity.User;
import com.lms.lms.repository.ProgressRepository;
import com.lms.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ProgressController {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}/progress")
    public ResponseEntity<List<Progress>> getProgressForUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Progress> progress = progressRepository.findByUser(user);
        return ResponseEntity.ok(progress);
    }
}
