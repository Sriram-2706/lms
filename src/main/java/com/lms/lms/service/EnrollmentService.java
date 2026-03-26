package com.lms.lms.service;

import com.lms.lms.entity.Course;
import com.lms.lms.entity.Enrollment;
import com.lms.lms.entity.User;
import com.lms.lms.repository.CourseRepository;
import com.lms.lms.repository.EnrollmentRepository;
import com.lms.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnrollmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enroll(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (enrollmentRepository.findByUserAndCourse(user, course).isPresent()) {
            throw new IllegalArgumentException("Already enrolled in course");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }
}
