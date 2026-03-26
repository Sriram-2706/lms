package com.lms.lms.controller;

import com.lms.lms.dto.AssignmentRequest;
import com.lms.lms.dto.CourseRequest;
import com.lms.lms.dto.ModuleRequest;
import com.lms.lms.entity.Assignment;
import com.lms.lms.entity.Course;
import com.lms.lms.entity.CourseModule;
import com.lms.lms.entity.User;
import com.lms.lms.repository.AssignmentRepository;
import com.lms.lms.repository.CourseModuleRepository;
import com.lms.lms.repository.CourseRepository;
import com.lms.lms.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Course> createCourse(@RequestBody @Valid CourseRequest request, Principal principal) {
        User instructor = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Instructor not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .instructor(instructor)
                .build();

        Course saved = courseRepository.save(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/modules")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseModule> addModule(@PathVariable Long courseId,
                                                  @RequestBody @Valid ModuleRequest request,
                                                  Principal principal) {
        userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Instructor not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        CourseModule module = CourseModule.builder()
                .course(course)
                .title(request.getTitle())
                .order(request.getOrder())
                .build();

        CourseModule saved = courseModuleRepository.save(module);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/modules/{moduleId}/assignments")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Assignment> addAssignment(@PathVariable Long moduleId,
                                                    @RequestBody @Valid AssignmentRequest request,
                                                    Principal principal) {
        userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Instructor not found"));

        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        Assignment assignment = Assignment.builder()
                .module(module)
                .title(request.getTitle())
                .maxScore(request.getMaxScore())
                .dueDate(request.getDueDate())
                .build();

        Assignment saved = assignmentRepository.save(assignment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Course>> getCoursesByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Course> courses;
        if (user.getRole().name().equals("INSTRUCTOR")) {
            courses = courseRepository.findByInstructor(user);
        } else {
            courses = courseRepository.findAll();
        }

        return ResponseEntity.ok(courses);
    }
}
