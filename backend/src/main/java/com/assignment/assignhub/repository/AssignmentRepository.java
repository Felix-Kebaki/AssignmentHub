package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository <Assignment,Long> {
    List<Assignment> findByUnitInstructorId(Long instructorId);
    @Query("SELECT DISTINCT a FROM Assignment a " +
            "JOIN a.unit u " +
            "JOIN u.courses c " +
            "JOIN c.students s " +
            "WHERE s.id = :studentId")
    List<Assignment> findAssignmentsByStudentId(@Param("studentId") Long studentId);
}
