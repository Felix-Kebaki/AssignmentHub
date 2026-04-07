package com.assignment.assignhub.controller;

import com.assignment.assignhub.dto.UnitResponse;
import com.assignment.assignhub.model.Role;
import com.assignment.assignhub.model.Unit;
import com.assignment.assignhub.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("units")
@CrossOrigin("http://localhost:5173")
public class UnitController {

    UnitService unitService;
    public UnitController(UnitService unitService){
        this.unitService=unitService;
    }

    @PostMapping("/createUnit")
    public ResponseEntity<String> createUnit(@RequestBody Unit unit){
        return new ResponseEntity<>(unitService.createUnit(unit), HttpStatus.CREATED);
    }

    @GetMapping("/getAllUnits")
    public ResponseEntity<List<UnitResponse>> getAllUnits(){
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @DeleteMapping("/deleteUnit/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable Long id){
        return ResponseEntity.ok(unitService.deleteUnit(id));
    }

    @GetMapping("/getYourUnits")
    public ResponseEntity<List<UnitResponse>> getYourUnits(@RequestParam(required = false) Role role){
        if(role != null ){
            return new ResponseEntity<>(unitService.getInstructorUnits(),HttpStatus.OK);
        }
        return new ResponseEntity<>(unitService.getYourUnits(),HttpStatus.OK);
    }
}
