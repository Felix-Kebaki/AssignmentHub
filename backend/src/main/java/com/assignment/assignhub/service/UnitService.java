package com.assignment.assignhub.service;

import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.exception.*;
import com.assignment.assignhub.mapper.UnitMapper;
import com.assignment.assignhub.model.*;
import com.assignment.assignhub.repository.CourseRepository;
import com.assignment.assignhub.repository.UnitRepository;
import com.assignment.assignhub.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    UnitRepository unitRepository;
    CloudinaryService cloudinaryService;
    public UnitService(
            UnitRepository unitRepository,
            UserRepository userRepository,
            CourseRepository courseRepository,
            CloudinaryService cloudinaryService
    ){
        this.unitRepository=unitRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.cloudinaryService=cloudinaryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String createUnit(Unit unit){
        if(unit.getUnitCode()==null ||
                unit.getUnitName()==null ||
                unit.getInstructorEmail()==null ||
                unit.getCourseList()==null ){
            throw new FormIsIncompleteException("Input all fields");
        }

        if(unitRepository.findByUnitName(unit.getUnitName()).isPresent() ||
                unitRepository.findByUnitCode(unit.getUnitCode()).isPresent()){
            throw new AlreadyExistsException("Unit already exists");
        }

        User user=userRepository.findByEmail(unit.getInstructorEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find instructor "+unit.getInstructorEmail()));
        if(!user.getRole().equals(Role.INSTRUCTOR)){
            throw new UnauthorizedException("User "+unit.getInstructorEmail()+" is not an instructor");
        }
        unit.setInstructor(user);

        List<Course> coursesToAdd = new ArrayList<>();
        List<String> missingCourses = new ArrayList<>();
        for(String courseName: unit.getCourseList()){
            Optional<Course> foundCourse=courseRepository.findByCourseName(courseName);
            if(foundCourse.isPresent()){
                coursesToAdd.add(foundCourse.get());
            }else{
                missingCourses.add(courseName);
            }
        }
        if(!missingCourses.isEmpty()){
            throw new NotFoundException("Cannot find course(s) "+String.join(", ",missingCourses));
        }
        unit.setCourses(coursesToAdd);

        try{
            unitRepository.save(unit);
            return "Unit created successfully";
        }catch(Exception e){
            throw new OperationFailException("Unable to create unit");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR')")
    public List<UnitResponse> getAllUnits(){
        try{
            return unitRepository.findAll()
                    .stream()
                    .map(UnitMapper::toDTO)
                    .toList();
        }catch(Exception e){
            throw new OperationFailException("Unable to fetch units");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUnit(Long id){
        Unit unit=unitRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Cannot find unit with id "+id));

        for(Assignment assignment: unit.getAssignments()){
            cloudinaryService.deleteFiles(assignment.getAssignmentPublicIds());
            for(Submission sub:assignment.getSubmissions()){
                cloudinaryService.deleteFiles(sub.getSubmissionPublicIds());
            }
        }

        try{
            unitRepository.deleteById(id);
            return "Unit deleted successfully";
        }catch(Exception e){
            throw new OperationFailException("Unable to delete unit");
        }
    }
}
