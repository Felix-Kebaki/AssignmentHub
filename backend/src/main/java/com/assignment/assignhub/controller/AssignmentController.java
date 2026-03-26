package com.assignment.assignhub.controller;

import com.assignment.assignhub.model.Assignment;
import com.assignment.assignhub.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("assignments")
public class AssignmentController {

    AssignmentService assignmentService;
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService=assignmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment){
        return new ResponseEntity<>(assignmentService.createAssignment(assignment), HttpStatus.CREATED);
    }
}
