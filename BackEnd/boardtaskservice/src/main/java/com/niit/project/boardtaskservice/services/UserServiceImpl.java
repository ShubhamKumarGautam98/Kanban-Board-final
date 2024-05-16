package com.niit.project.boardtaskservice.services;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.TaskNotFoundException;
import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;
import com.niit.project.boardtaskservice.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private final ITaskRepository taskRepository;
@Autowired
    public UserServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTaskFromUser(String emailId)throws TaskNotFoundException, UserNotFoundException {
        Optional<UserDetails> userOptional=taskRepository.findById(emailId);
        if(userOptional.isPresent()){
            UserDetails fetchedUser=userOptional.get();
            List<Task> taskList=fetchedUser.getTaskList();
            if(taskList==null || taskList.isEmpty() ) {
                throw new TaskNotFoundException("TaskList with Specified Id is not Found  ");
            }
            else{
                return taskList;

            }
        }
        else {
            throw new UserNotFoundException("user with the specified Id not found");
        }
    }

    @Override
    public Task getOneTaskById(UUID taskId, String emailId) throws TaskNotFoundException, UserNotFoundException {
        Optional<UserDetails> userOptional = taskRepository.findById(emailId);
        if (userOptional.isPresent()) {
            UserDetails fetchedUser = userOptional.get();
            List<Task> taskList = fetchedUser.getTaskList();
            if (taskList == null || taskList.isEmpty()) {
                throw new TaskNotFoundException("TaskList with Specified Id is not Found");
            } else {
                for (Task oneTask : taskList) {
                    if (oneTask.getTaskId().equals(taskId)) {
                        return oneTask;
                    }
                }
                throw new TaskNotFoundException("Task with the specified Id not found");
            }
        } else {
            throw new UserNotFoundException("user with the specified Id not found");
        }
    }

}
