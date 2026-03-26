package com.lms.lms.service;

import com.lms.lms.dto.CourseResponse;
import com.lms.lms.entity.Course;
import com.lms.lms.entity.Enrollment;
import com.lms.lms.entity.User;
import com.lms.lms.entity.UserRole;
import com.lms.lms.repository.CourseRepository;
import com.lms.lms.repository.EnrollmentRepository;
import com.lms.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Course createCourse(Long instructorId, String title, String description) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        if (instructor.getRole() != UserRole.INSTRUCTOR) {
            throw new IllegalArgumentException("User is not an instructor");
        }

        Course course = Course.builder()
                .title(title)
                .description(description)
                .instructor(instructor)
                .build();

        return courseRepository.save(course);
    }

    public List<Course> listAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> listCoursesForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole() == UserRole.INSTRUCTOR) {
            return courseRepository.findByInstructor(user);
        }

        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }

    public static CourseResponse toDto(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructor().getId()
        );
    }
}
