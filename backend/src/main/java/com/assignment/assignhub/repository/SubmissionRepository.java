package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssignment_id(Long id);
}
