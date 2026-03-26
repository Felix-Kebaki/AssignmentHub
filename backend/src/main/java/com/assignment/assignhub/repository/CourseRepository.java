package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByCourseName(String courseName);
}
