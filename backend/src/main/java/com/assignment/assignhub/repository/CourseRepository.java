package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
