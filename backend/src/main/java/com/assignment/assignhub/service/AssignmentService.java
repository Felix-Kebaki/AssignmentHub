package com.assignment.assignhub.service;

import com.assignment.assignhub.dto.AssignmentResponse;
import com.assignment.assignhub.exception.FormIsIncompleteException;
import com.assignment.assignhub.exception.NotFoundException;
import com.assignment.assignhub.exception.OperationFailException;
import com.assignment.assignhub.mapper.AssignmentMapper;
import com.assignment.assignhub.model.Assignment;
import com.assignment.assignhub.model.Unit;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.repository.AssignmentRepository;
import com.assignment.assignhub.repository.UnitRepository;
import com.assignment.assignhub.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentService {
    private final com.cloudinary.Cloudinary cloudinary;
    private final UserRepository userRepository;
    UnitRepository unitRepository;
    AssignmentRepository assignmentRepository;
    CloudinaryService cloudinaryService;
    public AssignmentService(AssignmentRepository assignmentRepository,
                             UnitRepository unitRepository,
                             com.cloudinary.Cloudinary cloudinary,
                             UserRepository userRepository,
                             CloudinaryService cloudinaryService
    ){
        this.assignmentRepository=assignmentRepository;
        this.unitRepository = unitRepository;
        this.cloudinary = cloudinary;
        this.userRepository = userRepository;
        this.cloudinaryService=cloudinaryService;
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    public String createAssignment(
            String assignmentName,
            String fileType,
            List<MultipartFile> files,
            Long id
    ){
        if(assignmentName ==null || fileType==null ){
            throw new FormIsIncompleteException("Input all fields");
        }

        if(!fileType.equals("Link") && (files==null || files.isEmpty())){
            throw new FormIsIncompleteException("Upload files");
        }

        Unit unit=unitRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Cannot find unit with id "+id));

//        if(unit.)

        Assignment assignment=new Assignment();
        assignment.setAssignmentName(assignmentName);

        List<String> publicIds = new ArrayList<>();
        List<String> fileUrls  = new ArrayList<>();
        try{
            for (MultipartFile file : files) {
                Map<String, Object> options = new HashMap<>();
                options.put("resource_type", "auto");
                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        options
                );

                String url = uploadResult.get("secure_url").toString();
                String publicId = uploadResult.get("public_id").toString();

                fileUrls.add(url);
                publicIds.add(publicId);
            }

            assignment.setFileUrls(fileUrls);
            assignment.setAssignmentPublicIds(publicIds);
            assignment.setResourceType(fileType);

            assignment.setUnit(unit);
            unit.getAssignments().add(assignment);

            assignmentRepository.save(assignment);

            return "Assignment created successfully";
        }catch (IOException e){
            throw new OperationFailException("Unable to create assignment");
        }
    }

    public Assignment getAssignment(Long id){
        return assignmentRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Cannot find assignment with id "+id));
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    public List<AssignmentResponse> instructorViewAssignments(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User user=userRepository.findByEmail(auth.getName())
                .orElseThrow(()->new NotFoundException("Cannot find user "+auth.getName()));
        try {
            return assignmentRepository.findByUnitInstructorId(user.getId())
                    .stream()
                    .map(AssignmentMapper::toDTO)
                    .toList();
        }catch(Exception e){
            throw new OperationFailException("Unable to fetch assignments");
        }
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    public String deleteAssignment(Long id){
        Assignment assignment=assignmentRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Cannot find assignment with id "+id));
        String res;
        if(assignment.getResourceType().equals("Document")){
            res="raw";
        }else if(assignment.getResourceType().equals("Photo")){
            res="image";
        }else{
            res="video";
        }

        try{
            cloudinaryService.deleteFiles(assignment.getAssignmentPublicIds(),res);
            assignmentRepository.deleteById(id);
            return "Assignment deleted successfully";
        }catch(Exception e){
            throw new OperationFailException("Unable to delete assignment");
        }
    }
}
