package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Role;
import com.assignment.assignhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.course c " +
            "LEFT JOIN FETCH c.units " +
            "WHERE u.email = :email")
    Optional<User> findByEmailWithCourse(@Param("email") String email);
}

