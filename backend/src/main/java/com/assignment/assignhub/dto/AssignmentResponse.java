package com.assignment.assignhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentResponse {
    private Long id;
    private String assignmentName;
    private String resourceType;
    private List<String> fileUrls;
}
