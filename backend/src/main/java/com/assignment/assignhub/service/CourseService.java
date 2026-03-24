package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.FormIsIncompleteException;
import com.assignment.assignhub.exception.NotFoundException;
import com.assignment.assignhub.exception.OperationFailException;
import com.assignment.assignhub.model.Course;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.repository.CourseRepository;
import com.assignment.assignhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    CourseRepository courseRepository;
    UserRepository userRepository;
    public CourseService(CourseRepository courseRepository,UserRepository userRepository){
        this.courseRepository=courseRepository;
        this.userRepository=userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String createCourse(Course course){
        if(course.getCourseName()==null){
            throw new FormIsIncompleteException("Input all fields");
        }
        try {
            courseRepository.save(course);
            return "Course added successfully";
        }catch(Exception e){
            throw new OperationFailException("Unable to create course");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String deleteCourse(Long id){
        if(!courseRepository.existsById(id)){
            throw new NotFoundException("Cannot find course with id "+id);
        }
        try{
            courseRepository.deleteById(id);
            return "Deleted successfully";
        }catch (Exception e){
            throw new OperationFailException("Unable to delete course");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public List<Course> getAllCourses(){
        try {
            return courseRepository.findAll();
        }catch(Exception e){
            throw new NotFoundException("Unable to fetch courses");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String assignStudentCourse(Long courseId,Long userId){
        User student=userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("Cannot find user with id "+userId));

        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new NotFoundException("Cannot find course with id "+courseId));

        try {
            student.setCourse(course);
            userRepository.save(student);
            return "Assigned course successfully";
        }catch(Exception e){
            throw new OperationFailException("Unable to assign course");
        }
    }
}
