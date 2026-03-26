package com.lms.lms.service;

import com.lms.lms.dto.AssignmentResponse;
import com.lms.lms.entity.Assignment;
import com.lms.lms.entity.CourseModule;
import com.lms.lms.repository.AssignmentRepository;
import com.lms.lms.repository.CourseModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private CourseModuleRepository moduleRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment addAssignment(Long moduleId, String title, Integer maxScore, java.time.LocalDateTime dueDate) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        Assignment assignment = Assignment.builder()
                .module(module)
                .title(title)
                .maxScore(maxScore)
                .dueDate(dueDate)
                .build();

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> listByModule(Long moduleId) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));
        return assignmentRepository.findByModule(module);
    }

    public static AssignmentResponse toDto(Assignment assignment) {
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getModule().getId(),
                assignment.getTitle(),
                assignment.getMaxScore(),
                assignment.getDueDate()
        );
    }
}
