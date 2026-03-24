package com.assignment.assignhub.exception;

public class UserIncorrectPasswordException extends RuntimeException{
    public UserIncorrectPasswordException(String message){
        super(message);
    }
}
