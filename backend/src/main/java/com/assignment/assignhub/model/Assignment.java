package com.assignment.assignhub.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignmentName;
    private String fileUrl;

    @Transient
    private String fileType;
    private String assignmentPublicId;
    private String assignmentType;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
