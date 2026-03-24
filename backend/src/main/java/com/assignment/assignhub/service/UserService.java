package com.assignment.assignhub.service;

import com.assignment.assignhub.exception.UserAlreadyExistsException;
import com.assignment.assignhub.exception.UserFormIsIncompleteException;
import com.assignment.assignhub.exception.UserIncorrectPasswordException;
import com.assignment.assignhub.exception.UserNotFoundException;
import com.assignment.assignhub.model.User;
import com.assignment.assignhub.model.UserPrincipal;
import com.assignment.assignhub.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Cannot find user with email " + username);
        }
        return new UserPrincipal(user.get());
    }

    public String registerUser(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null || user.getEmail() == null) {
            throw new UserFormIsIncompleteException("Input all fields");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Account already exists with email " + user.getEmail());
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
                throw new UserFormIsIncompleteException("Input all fields");
            }

            if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
                throw new UserNotFoundException("Cannot find user with email " + user.getEmail());
            }

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            }else {
                throw new UserIncorrectPasswordException("Incorrect password");
            }
        }catch(BadCredentialsException ex){
            throw new UserIncorrectPasswordException("Incorrect password");
        }
    }

    public User getProfile(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id " + id));
    }

    public User updateProfile(Long id, User user) {
        Optional<User> fetchedUser = userRepository.findById(id);
        if (fetchedUser.isEmpty()) {
            throw new UserNotFoundException("Cannot find user with id " + id);
        }
        return new User();
    }
}
