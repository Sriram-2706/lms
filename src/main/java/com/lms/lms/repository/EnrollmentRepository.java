package com.lms.lms.repository;

import com.lms.lms.entity.Enrollment;
import com.lms.lms.entity.Course;
import com.lms.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserAndCourse(User user, Course course);
    List<Enrollment> findByUser(User user);
    List<Enrollment> findByCourse(Course course);
}
