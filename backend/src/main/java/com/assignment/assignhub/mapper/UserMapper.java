package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.dto.UserResponse;
import com.assignment.assignhub.model.User;

import java.util.List;

public class UserMapper {
    public static UserResponse toDTO(User user) {
        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        if (user.getCourse() != null) {
            dto.setCourseName(user.getCourse().getCourseName());

            List<UnitResponse> units = user.getCourse().getUnits()
                    .stream()
                    .map(unit -> {
                        UnitResponse un=new UnitResponse();
                            un.setId(unit.getId());
                            un.setCourseCode(unit.getUnitCode());
                            un.setCourseName(unit.getUnitName());
                            un.setInstructorName(unit.getInstructor().getFirstName() + " "+ unit.getInstructor().getLastName());
                            return un;
                    })
                    .toList();

            dto.setUnits(units);
        }
        return dto;
    }
}
