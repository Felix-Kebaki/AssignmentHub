package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.AssignmentResponse;
import com.assignment.assignhub.model.Assignment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AssignmentMapper {
    public static AssignmentResponse toDTO(Assignment assignment) {
        AssignmentResponse dto = new AssignmentResponse();

        dto.setId(assignment.getId());
        dto.setAssignmentName(assignment.getAssignmentName());
        dto.setResourceType(assignment.getResourceType());
        dto.setFileUrls(assignment.getFileUrls());
        dto.setUnitName(assignment.getUnit().getUnitName());
        dto.setUnitCode(assignment.getUnit().getUnitCode());
        dto.setDueDate(assignment.getDueDate());

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        boolean isInstructor = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_INSTRUCTOR"));
        if(isInstructor) {
            dto.setSubmissionsMade(assignment.getSubmissions() != null ? assignment.getSubmissions().size() : 0);
        }else{
            dto.setSubmissionsMade(null);
        }

        return dto;
    }
}
