package com.assignment.assignhub.repository;

import com.assignment.assignhub.dto.UserResponse;
import com.assignment.assignhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);
    List<UserResponse> findByRole(String role);
}

