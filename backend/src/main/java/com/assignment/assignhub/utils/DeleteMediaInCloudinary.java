package com.assignment.assignhub.utils;

import com.assignment.assignhub.model.Assignment;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DeleteMediaInCloudinary {

    private final com.cloudinary.Cloudinary cloudinary;
    DeleteMediaInCloudinary(com.cloudinary.Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }
    public void deleteAssignmentFiles(Assignment assignment) {
        if (assignment.getAssignmentPublicIds() != null) {
            for (String publicId : assignment.getAssignmentPublicIds()) {
                try {
                    cloudinary.uploader().destroy(publicId, new HashMap<>());
                } catch (Exception e) {
                    System.out.println("Failed to delete file: " + publicId + " - " + e.getMessage());
                }
            }
        }
    }
}
