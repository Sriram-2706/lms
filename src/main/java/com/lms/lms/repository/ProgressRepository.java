package com.lms.lms.repository;

import com.lms.lms.entity.Progress;
import com.lms.lms.entity.User;
import com.lms.lms.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByUserAndModule(User user, CourseModule module);
    List<Progress> findByUser(User user);
    List<Progress> findByModule(CourseModule module);
}
