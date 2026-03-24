package com.assignment.assignhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(UserNotFoundException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(UserFormIsIncompleteException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(UserFormIsIncompleteException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(UserAlreadyExistsException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(UserIncorrectPasswordException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(UserIncorrectPasswordException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
    }
}
