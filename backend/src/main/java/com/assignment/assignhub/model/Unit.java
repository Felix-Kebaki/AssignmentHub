package com.assignment.assignhub.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unitCode;
    private String unitName;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Transient
    private String instructorEmail;

    @Transient
    private List<String> courseList;

    @ManyToMany
    @JoinTable(
            name = "unit_courses",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses=new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Assignment> assignments = new ArrayList<>();
}
