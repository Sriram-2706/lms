package com.lms.lms.service;

import com.lms.lms.dto.ProgressResponse;
import com.lms.lms.entity.CourseModule;
import com.lms.lms.entity.Progress;
import com.lms.lms.entity.ProgressStatus;
import com.lms.lms.entity.User;
import com.lms.lms.repository.CourseModuleRepository;
import com.lms.lms.repository.ProgressRepository;
import com.lms.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseModuleRepository moduleRepository;

    @Autowired
    private ProgressRepository progressRepository;

    public Progress createOrUpdateProgress(Long userId, Long moduleId, ProgressStatus status, Integer score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        Progress progress = progressRepository.findByUserAndModule(user, module)
                .orElse(Progress.builder()
                        .user(user)
                        .module(module)
                        .status(ProgressStatus.NOT_STARTED)
                        .build());

        progress.setStatus(status);
        progress.setScore(score);

        if (status == ProgressStatus.COMPLETED) {
            progress.setCompletedAt(LocalDateTime.now());
        }

        return progressRepository.save(progress);
    }

    public List<Progress> getProgressForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return progressRepository.findByUser(user);
    }

    public static ProgressResponse toDto(Progress progress) {
        return new ProgressResponse(
                progress.getId(),
                progress.getUser().getId(),
                progress.getModule().getId(),
                progress.getStatus(),
                progress.getScore(),
                progress.getCompletedAt()
        );
    }
}
