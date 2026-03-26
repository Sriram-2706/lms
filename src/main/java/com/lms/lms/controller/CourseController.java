package com.lms.lms.controller;

import com.lms.lms.dto.*;
import com.lms.lms.entity.Assignment;
import com.lms.lms.entity.Course;
import com.lms.lms.entity.CourseModule;
import com.lms.lms.service.AssignmentService;
import com.lms.lms.service.CourseModuleService;
import com.lms.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseModuleService courseModuleService;

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        Course course = courseService.createCourse(request.getInstructorId(), request.getTitle(), request.getDescription());
        return ResponseEntity.ok(CourseService.toDto(course));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> result = courseService.listAllCourses().stream()
                .map(CourseService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<List<CourseResponse>> getCoursesForUser(@PathVariable Long userId) {
        List<CourseResponse> result = courseService.listCoursesForUser(userId).stream()
                .map(CourseService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<CourseModuleResponse> addModule(@PathVariable Long courseId, @RequestBody CreateModuleRequest request) {
        CourseModule module = courseModuleService.addModule(courseId, request.getTitle(), request.getOrder());
        return ResponseEntity.ok(CourseModuleService.toDto(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<CourseModuleResponse>> listModules(@PathVariable Long courseId) {
        List<CourseModuleResponse> modules = courseModuleService.listModulesByCourse(courseId).stream()
                .map(CourseModuleService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/modules/{moduleId}/assignments")
    public ResponseEntity<AssignmentResponse> addAssignment(@PathVariable Long moduleId, @RequestBody CreateAssignmentRequest request) {
        Assignment assignment = assignmentService.addAssignment(moduleId, request.getTitle(), request.getMaxScore(), request.getDueDate());
        return ResponseEntity.ok(AssignmentService.toDto(assignment));
    }

    @GetMapping("/modules/{moduleId}/assignments")
    public ResponseEntity<List<AssignmentResponse>> listAssignments(@PathVariable Long moduleId) {
        List<AssignmentResponse> assignments = assignmentService.listByModule(moduleId).stream()
                .map(AssignmentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assignments);
    }
}
