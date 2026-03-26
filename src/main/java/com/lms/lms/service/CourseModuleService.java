package com.lms.lms.service;

import com.lms.lms.dto.CourseModuleResponse;
import com.lms.lms.entity.Course;
import com.lms.lms.entity.CourseModule;
import com.lms.lms.repository.CourseModuleRepository;
import com.lms.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseModuleService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseModuleRepository moduleRepository;

    public CourseModule addModule(Long courseId, String title, Integer order) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        CourseModule module = CourseModule.builder()
                .title(title)
                .course(course)
                .order(order)
                .build();

        return moduleRepository.save(module);
    }

    public List<CourseModule> listModulesByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        return moduleRepository.findByCourse(course);
    }

    public static CourseModuleResponse toDto(CourseModule module) {
        return new CourseModuleResponse(
                module.getId(),
                module.getCourse().getId(),
                module.getTitle(),
                module.getOrder()
        );
    }
}
