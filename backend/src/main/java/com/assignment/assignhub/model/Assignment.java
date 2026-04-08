package com.assignment.assignhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignmentName;
    private String link;

    private LocalDate dueDate;

    @ElementCollection
    private List<String> fileUrls = new ArrayList<>();

    @Transient
    private String fileType;

    @ElementCollection
    private List<String> assignmentPublicIds = new ArrayList<>();
    private String resourceType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Submission> submissions;
}
