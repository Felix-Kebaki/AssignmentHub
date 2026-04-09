package com.assignment.assignhub.controller;

import com.assignment.assignhub.dto.SubmissionResponse;
import com.assignment.assignhub.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("submissions")
public class SubmissionController {

    SubmissionService submissionService;
    public SubmissionController(SubmissionService submissionService){
        this.submissionService=submissionService;
    }

    @PostMapping("/makeSubmission/{id}")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("fileType") String fileType,
            @PathVariable Long id
    ){
        return new ResponseEntity<>(submissionService.submitAssignment(id,fileType,files), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubmission/{id}")
    public ResponseEntity<String> deleteSubmission(@PathVariable Long id){
        return new ResponseEntity<>(submissionService.deleteSubmission(id),HttpStatus.OK);
    }

    @GetMapping("/getSubmissions/{id}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissions(@PathVariable Long id){
        return new ResponseEntity<>(submissionService.getSubmissions(id),HttpStatus.OK);
    }

}
