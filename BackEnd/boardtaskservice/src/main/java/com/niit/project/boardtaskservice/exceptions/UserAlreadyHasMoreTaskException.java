package com.niit.project.boardtaskservice.exceptions;

public class UserAlreadyHasMoreTaskException extends Throwable {
     UserAlreadyHasMoreTaskException (String message){
         super(message);
     }
}
