package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.AssignmentResponse;
import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.model.Course;
import com.assignment.assignhub.model.Unit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UnitMapper {
    public static UnitResponse toDTO(Unit unit) {
        UnitResponse dto = new UnitResponse();
        dto.setId(unit.getId());
        dto.setCourseCode(unit.getUnitCode());
        dto.setCourseName(unit.getUnitName());

        if (unit.getInstructor() != null) {
            dto.setInstructorName(unit.getInstructor().getFirstName() + " " + unit.getInstructor().getLastName());
        }

        if (unit.getCourses() != null) {
            dto.setCourseNames(
                    unit.getCourses().stream()
                            .map(Course::getCourseName)
                            .toList()
            );
        }
        if (unit.getAssignments() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isInstructor = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_INSTRUCTOR"));

            dto.setAssignments(
                    unit.getAssignments().stream()
                            .map(assignment -> {
                                AssignmentResponse ar = new AssignmentResponse();
                                ar.setId(assignment.getId());
                                ar.setAssignmentName(assignment.getAssignmentName());
                                ar.setResourceType(assignment.getResourceType());
                                ar.setFileUrls(assignment.getFileUrls());
                                if (isInstructor) {
                                    ar.setSubmissionsMade(assignment.getSubmissions() != null ? assignment.getSubmissions().size() : 0);
                                }

                                return ar;
                            })
                            .toList()
            );
        }

        return dto;
    }
}
