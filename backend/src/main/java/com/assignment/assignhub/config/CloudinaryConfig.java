package com.assignment.assignhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private  String cloud_name;
    private String api_key;
    private String api_secret;
    public CloudinaryConfig(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_secret}") String apiSecret,
            @Value("${cloudinary.api_key}") String apiKey
    ) {
        this.api_key=apiKey;
        this.api_secret=apiSecret;
        this.cloud_name=cloudName;
    }

    @Bean
    public com.cloudinary.Cloudinary cloudinary() {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);

        return new com.cloudinary.Cloudinary(config);
    }
}
