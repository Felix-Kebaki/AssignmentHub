package com.assignment.assignhub.controller;

import com.assignment.assignhub.dto.AssignmentResponse;
import com.assignment.assignhub.model.Assignment;
import com.assignment.assignhub.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("assignments")
@CrossOrigin("http://localhost:5173")
public class AssignmentController {

    AssignmentService assignmentService;
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService=assignmentService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<String> createAssignment(
            @RequestParam("fileType") String fileType,
            @RequestParam("assignmentName") String assignmentName,
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable Long id){
        return new ResponseEntity<>(assignmentService.createAssignment(assignmentName,fileType,files,id), HttpStatus.CREATED);
    }

    @GetMapping("/getAssignment/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long id){
        return new ResponseEntity<>(assignmentService.getAssignment(id),HttpStatus.OK);
    }

//    @GetMapping("InstructorAssignmentView/{email}")
//    public ResponseEntity<List<AssignmentResponse>> instructorViewAssignments(@PathVariable String email){
//        return new ResponseEntity<>(assignmentService.instructorViewAssignments(email));
//    }

    @DeleteMapping("/deleteAssignment/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id){
        return new ResponseEntity<>(assignmentService.deleteAssignment(id),HttpStatus.OK);
    }
}
