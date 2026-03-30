package com.assignment.assignhub.controller;

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

    @PostMapping("/makeSubmission")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("fileType") String fileType
    ){
        return new ResponseEntity<>(submissionService.submitAssignment(fileType,files), HttpStatus.OK);
    }
}
