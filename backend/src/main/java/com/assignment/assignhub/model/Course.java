package com.assignment.assignhub.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;

    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) //mapped used in only this and many to many,it tells spring the relationship is controlled by who
    private List<User> students;

    @ManyToMany(mappedBy = "courses")
    private List<Unit> units=new ArrayList<>();
}
