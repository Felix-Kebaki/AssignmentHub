package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.*;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.model.UserPrincipal;
import com.assignment.assignhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new NotFoundException("Cannot find user with email " + username);
        }
        return new UserPrincipal(user.get());
    }

    public String registerUser(User user) {
        if (user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getPassword() == null ||
                user.getEmail() == null ||
                user.getRole() == null) {
            throw new FormIsIncompleteException("Input all fields");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Account already exists with email " + user.getEmail());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        if (createdUser != null) {
            return jwtService.generateToken(createdUser.getEmail());
        }
        return "Unable to create user";
    }

    public String loginUser(User user, AuthenticationManager authManager) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                throw new FormIsIncompleteException("Input all fields");
            }

            if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
                throw new NotFoundException("Cannot find user with email " + user.getEmail());
            }

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            } else {
                throw new UnauthorizedException("Incorrect password");
            }
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Incorrect password");
        }
    }

    public User getProfile(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User requestedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find user with id " + id));

        if (!auth.getName().equals(requestedUser.getEmail())) {
            throw new UnauthorizedException("Unauthorized access");
        }

        return requestedUser;
    }

    @Transactional
    public String updateProfile(Long id, User user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Boolean emailChanged = false;

        User fetchedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find user with id " + id));

        if (!auth.getName().equals(fetchedUser.getEmail())) {
            throw new UnauthorizedException("Unauthorized access");
        }

        if (user.getEmail() != null && !user.getEmail().equals(fetchedUser.getEmail())) {
            fetchedUser.setEmail(user.getEmail());
            emailChanged = true;
        }
        if (user.getFirstName() != null) {
            fetchedUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            fetchedUser.setLastName(user.getLastName());
        }
        try {
            userRepository.save(fetchedUser);
            if (emailChanged) {
                String newToken = jwtService.generateToken(fetchedUser.getEmail());
                return newToken;
            }
            return "Updated successfully";
        } catch (Exception e) {
            throw new OperationFailException("Unable to update user");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NotFoundException("Cannot find user with id "+id);
        }

        try{
            userRepository.deleteById(id);
            return "Deleted successfully";
        }catch (Exception e){
            throw new OperationFailException("Unable to delete user with id "+id);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){
        try{
            return userRepository.findAll();
        }catch (Exception e){
            throw new NotFoundException("Unable to fetch users");
        }
    }
}
