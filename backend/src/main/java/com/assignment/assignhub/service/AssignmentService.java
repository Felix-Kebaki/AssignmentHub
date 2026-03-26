package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.FormIsIncompleteException;
import com.assignment.assignhub.model.Assignment;
import com.assignment.assignhub.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    AssignmentRepository assignmentRepository;
    public AssignmentService(AssignmentRepository assignmentRepository){
        this.assignmentRepository=assignmentRepository;
    }

    public String createAssignment(Assignment assignment){
        if(assignment.getAssignmentName()==null || assignment.getFileUrl()==null || assignment.getFileType()==null){
            throw new FormIsIncompleteException("Input all fields");
        }
        return "";
    }
}
