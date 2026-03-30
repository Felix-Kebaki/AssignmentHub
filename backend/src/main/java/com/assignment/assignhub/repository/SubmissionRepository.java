package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
