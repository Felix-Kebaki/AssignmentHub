package com.assignment.assignhub.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String errorMessage;
}
