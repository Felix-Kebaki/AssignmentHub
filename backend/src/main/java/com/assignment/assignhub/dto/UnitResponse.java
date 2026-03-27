package com.assignment.assignhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnitResponse {
    private Long id;
    private String courseCode;
    private String courseName;
    private String instructorName;
    private List<String> courseNames;
    private List<AssignmentResponse> assignments;
}
