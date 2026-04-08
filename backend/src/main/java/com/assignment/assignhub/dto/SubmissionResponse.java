package com.assignment.assignhub.dto;

import com.assignment.assignhub.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionResponse {
    private Long id;
    private List<String> fileUrls;
    private List<String> submissionPublicIds;
    private String submissionType;
    private LocalDateTime submittedAt;

    private Long assignmentId;
    private String assignmentName;

    private String submittedByEmail;
    private String submittedByName;
}
