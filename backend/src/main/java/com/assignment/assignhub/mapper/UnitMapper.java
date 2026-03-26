package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.model.Course;
import com.assignment.assignhub.model.Unit;

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

        return dto;
    }
}
