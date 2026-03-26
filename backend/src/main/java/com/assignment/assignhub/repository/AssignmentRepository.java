package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository <Assignment,Long> {
}
