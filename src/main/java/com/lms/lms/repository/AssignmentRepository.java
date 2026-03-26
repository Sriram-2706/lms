package com.lms.lms.repository;

import com.lms.lms.entity.Assignment;
import com.lms.lms.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByModule(CourseModule module);
}
