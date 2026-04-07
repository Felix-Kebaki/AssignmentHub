package com.assignment.assignhub.repository;

import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.model.Course;
import com.assignment.assignhub.model.Unit;
import com.assignment.assignhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit,Long> {
    Optional<Unit> findByUnitCode(String unitCode);
    Optional<Unit> findByUnitName(String unitName);
    List<Unit> findByCoursesContaining(Course course);
    List<Unit> findByInstructor(User instructor);
}
