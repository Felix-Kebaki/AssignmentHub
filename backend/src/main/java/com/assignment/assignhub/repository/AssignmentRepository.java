package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository <Assignment,Long> {
    List<Assignment> findByUnitInstructorId(Long instructorId);
}
