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
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(NotFoundException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(FormIsIncompleteException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(FormIsIncompleteException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(AlreadyExistsException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(UnauthorizedException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(OperationFailException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(OperationFailException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
