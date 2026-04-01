package com.assignment.assignhub.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public void deleteFiles(List<String> publicIds){
        if(publicIds==null || publicIds.isEmpty()){
            return;
        }

        for(String publicId:publicIds){
            try{
                Map<String,String> options=new HashMap<>();
                options.put("resource_type","auto");

                Map result=cloudinary.uploader().destroy(publicId,options);
                System.out.println("Deleted: "+publicId+ " -> "+result.get("result"));
            }catch(Exception e){
                System.out.println("Failed to delete "+publicId);
                e.printStackTrace();
            }
        }
    }
}
