package com.assignment.assignhub.mapper;


import com.assignment.assignhub.dto.SubmissionResponse;
import com.assignment.assignhub.model.Submission;

public class SubmissionMapper {
    public static SubmissionResponse toDTO(Submission submission) {
        SubmissionResponse dto = new SubmissionResponse();

        dto.setId(submission.getId());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setSubmittedByEmail(submission.getSubmittedBy().getEmail());
        dto.setFileUrls(submission.getFileUrls());
        dto.setSubmissionType(submission.getSubmissionType());
        dto.setSubmissionPublicIds(submission.getSubmissionPublicIds());
        dto.setAssignmentName(submission.getAssignment().getAssignmentName());
        dto.setAssignmentId(submission.getAssignment().getId());
        dto.setSubmittedByName(submission.getSubmittedBy().getFirstName()+ " " +submission.getSubmittedBy().getLastName());

        return dto;
    }
}
