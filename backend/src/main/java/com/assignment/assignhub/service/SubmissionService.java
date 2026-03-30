package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.FormIsIncompleteException;
import com.assignment.assignhub.exception.NotFoundException;
import com.assignment.assignhub.exception.OperationFailException;
import com.assignment.assignhub.model.Submission;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.repository.SubmissionRepository;
import com.assignment.assignhub.repository.UserRepository;
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

    public SubmissionService(SubmissionRepository submissionRepository, com.cloudinary.Cloudinary cloudinary, UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.cloudinary=cloudinary;
        this.userRepository=userRepository;
    }

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
}
