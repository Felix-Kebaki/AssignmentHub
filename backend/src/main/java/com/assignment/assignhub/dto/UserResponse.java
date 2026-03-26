package com.assignment.assignhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String courseName;
    private List<UnitResponse> units;
}
