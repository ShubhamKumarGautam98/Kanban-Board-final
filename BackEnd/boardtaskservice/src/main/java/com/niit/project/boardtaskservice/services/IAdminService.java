package com.niit.project.boardtaskservice.services;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.*;

import java.util.List;

public interface IAdminService {
    //
    UserDetails registerUser (UserDetails userdetails)throws UserAlreadyExistsException;
    UserDetails deleteUser(String emailId)throws UserNotFoundException;
    UserDetails saveTaskToUser(Task task, String userEmail) throws TaskAlreadyExistsException,UserAlreadyExistsException;
    UserDetails updateTaskToUser(Task task, String userEmail)throws TaskNotFoundException, UserNotFoundException,InvalidStatusChangeException;
    UserDetails deleteTask(Task task, String emailId)throws TaskNotFoundException;
    String getUserName(String emailId)throws UserNotFoundException;
    List<UserDetails> getAllUser(String emailId) throws UserNotFoundException;
    String getUserRole(String emailId) throws UserNotFoundException;
}
