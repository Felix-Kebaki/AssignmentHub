package com.assignment.assignhub.mapper;

import com.assignment.assignhub.dto.CourseResponse;
import com.assignment.assignhub.model.Course;

import java.util.stream.Collectors;

public class CourseMapper {
    public static CourseResponse toDTO(Course course) {
        CourseResponse dto = new CourseResponse();

        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());

        if (course.getStudents() != null) {
            dto.setStudentNames(
                    course.getStudents()
                            .stream()
                            .map(user -> user.getFirstName() + " " + user.getLastName())
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
