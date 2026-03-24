package com.assignment.assignhub.controller;

import com.assignment.assignhub.model.Course;
import com.assignment.assignhub.repository.CourseRepository;
import com.assignment.assignhub.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    CourseService courseService;
    public CourseController(CourseService courseService, CourseRepository courseRepository){
        this.courseService=courseService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createCourse(@RequestBody Course course){
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/assignCourseToStudent/{courseId}/{userId}")
    public ResponseEntity<String> assignStudentCourse(@PathVariable Long courseId,@PathVariable Long userId){
        return ResponseEntity.ok(courseService.assignStudentCourse(courseId,userId));
    }

}
