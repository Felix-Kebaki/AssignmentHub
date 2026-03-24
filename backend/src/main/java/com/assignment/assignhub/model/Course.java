package com.assignment.assignhub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<User> students;
}
