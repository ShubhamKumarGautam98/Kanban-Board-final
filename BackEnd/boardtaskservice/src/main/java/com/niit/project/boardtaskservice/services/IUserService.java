package com.niit.project.boardtaskservice.services;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.exceptions.TaskNotFoundException;
import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<Task> getAllTaskFromUser(String emailId)throws TaskNotFoundException, UserNotFoundException;
    Task getOneTaskById(UUID taskId, String emailId) throws TaskNotFoundException, UserNotFoundException;

}
