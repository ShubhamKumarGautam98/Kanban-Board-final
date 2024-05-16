package com.niit.project.boardtaskservice.exceptions;

public class TaskAlreadyExistsException extends RuntimeException{
    public TaskAlreadyExistsException(String message){
        super(message);
    }
}
