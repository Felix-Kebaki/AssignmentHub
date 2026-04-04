package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.FormIsIncompleteException;
import com.assignment.assignhub.exception.NotFoundException;
import com.assignment.assignhub.exception.OperationFailException;
import com.assignment.assignhub.exception.UnauthorizedException;
import com.assignment.assignhub.model.Submission;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.repository.SubmissionRepository;
import com.assignment.assignhub.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionService {

    SubmissionRepository submissionRepository;
    private final com.cloudinary.Cloudinary cloudinary;
    UserRepository userRepository;
    CloudinaryService cloudinaryService;

    public SubmissionService(SubmissionRepository submissionRepository,
                             com.cloudinary.Cloudinary cloudinary,
                             UserRepository userRepository,
                             CloudinaryService cloudinaryService) {
        this.submissionRepository = submissionRepository;
        this.cloudinary=cloudinary;
        this.userRepository=userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    public String submitAssignment(
            String fileType,
            List<MultipartFile> files
    ) {
        if (fileType == null ||
                files == null ||
                files.isEmpty()) {
            throw new FormIsIncompleteException("Input all fields");
        }

        Submission submission=new Submission();

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
            submission.setSubmissionType(fileType);
            submission.setSubmissionPublicIds(publicIds);
            submission.setFileUrls(fileUrls);
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setSubmittedBy(getCurrentUser());

            submissionRepository.save(submission);
            return "Submission made successfully";
        }catch (Exception e){
            throw new OperationFailException("Unable to submit");
        }
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    public String deleteSubmission(Long id){
        Submission sub=submissionRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Cannot find submission with id "+id));

        if(!getCurrentUser().equals(sub.getSubmittedBy())){
           throw new UnauthorizedException("Unauthorized access");
        }
        String resource;
        if(sub.getSubmissionType().equals("Document")){
            resource="raw";
        }else if(sub.getSubmissionType().equals("Photo")){
            resource="image";
        }else{
            resource="video";
        }

        try{
            cloudinaryService.deleteFiles(sub.getSubmissionPublicIds(),resource);
            submissionRepository.deleteById(id);
            return "Successfully deleted submission";
        }catch(Exception e){
            throw new OperationFailException("Unable to delete submission");
        }
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    public List<Submission> getSubmissions(Long id){
        try{
            return submissionRepository.findByAssignment_id(id);
        }catch (Exception e){
            throw new OperationFailException("Unable to get submissions");
        }
    }
}
