package com.assignment.assignhub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> fileUrls = new ArrayList<>();

    @Transient
    private String fileType;

    @ElementCollection
    private List<String> submissionPublicIds = new ArrayList<>();
    private String submissionType;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User submittedBy;

    private LocalDateTime submittedAt;
}
