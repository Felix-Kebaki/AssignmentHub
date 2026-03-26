package com.assignment.assignhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {
    private Long id;
    private String courseName;
    private List<String> studentNames;
}
