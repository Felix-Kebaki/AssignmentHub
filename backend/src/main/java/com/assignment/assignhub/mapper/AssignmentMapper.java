package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.AssignmentResponse;
import com.assignment.assignhub.model.Assignment;

public class AssignmentMapper {
    public static AssignmentResponse toDTO(Assignment assignment) {
        AssignmentResponse dto=new AssignmentResponse();

        dto.setId(assignment.getId());
        dto.setAssignmentName(assignment.getAssignmentName());
        dto.setResourceType(assignment.getResourceType());
        dto.setFileUrls(assignment.getFileUrls());
        dto.setUnitName(assignment.getUnit().getUnitName());
        dto.setUnitCode(assignment.getUnit().getUnitCode());
        return dto;
    }
}
