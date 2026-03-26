package com.assignment.assignhub.controller;

import com.assignment.assignhub.dto.AuthResponse;
import com.assignment.assignhub.dto.UserResponse;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("auth")
public class UserController {

    UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }

    @Autowired
    AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody User user){
        return ResponseEntity.ok(userService.loginUser(user,authManager));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id){
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id,@RequestBody User user){
        return ResponseEntity.ok(userService.updateProfile(id,user));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
