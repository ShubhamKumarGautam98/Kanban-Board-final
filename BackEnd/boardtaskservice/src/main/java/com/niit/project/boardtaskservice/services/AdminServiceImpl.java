package com.niit.project.boardtaskservice.services;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.*;
import com.niit.project.boardtaskservice.proxy.TaskProxy;
import com.niit.project.boardtaskservice.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements IAdminService{
    private final ITaskRepository taskRepository;
    private final TaskProxy taskProxy;
    @Autowired
    public AdminServiceImpl(ITaskRepository taskRepository, TaskProxy taskProxy) {
        this.taskRepository = taskRepository;
        this.taskProxy = taskProxy;
    }
    @Override
    public UserDetails registerUser(UserDetails userdetails)throws UserAlreadyExistsException {
        if(taskRepository.findById(userdetails.getEmailId()).isPresent()){
            throw new UserAlreadyExistsException("UserAlreadyExists");
        }
        else{
            UserDetails insertedUser=taskRepository.save(userdetails);
            taskProxy.registerUser(insertedUser);
            return insertedUser;
        }
    }



    @Override
    public UserDetails saveTaskToUser(Task task, String userEmail) throws TaskAlreadyExistsException, UserAlreadyExistsException {
        Optional<UserDetails> userOptional = taskRepository.findById(userEmail);
        if (userOptional.isPresent()) {
            UserDetails fetchedUser = userOptional.get();
            List<Task> taskList = fetchedUser.getTaskList();
            if (taskList == null) {
                fetchedUser.setTaskList(new ArrayList<>(Collections.singletonList(task))); // Initialize with a new ArrayList containing the task
            } else {
                boolean taskPresent = false;
                for (Task searchTask : taskList) {
                    if (searchTask.getTaskName().equals(task.getTaskName())) {
                        taskPresent = true;
                        break;
                    }
                }
                if (taskPresent) {
                    throw new TaskAlreadyExistsException("Task already present for the given email ID");
                } else {
                    taskList.add(task);
                    fetchedUser.setTaskList(taskList);
                    Optional<UserDetails> assignedToUserOptional = taskRepository.findById(task.getAssignTo());
                    if (assignedToUserOptional.isPresent()) {
                        UserDetails assignedToUser = assignedToUserOptional.get();
                        List<Task> assignedToTaskList = assignedToUser.getTaskList();
                        if (assignedToTaskList == null) {
                            assignedToTaskList = new ArrayList<>(); // Initialize with a new ArrayList if null
                        }
                        assignedToTaskList.add(task);
                        assignedToUser.setTaskList(assignedToTaskList);
                        taskRepository.save(assignedToUser); // Save the user who is assigned the task
                    }
                }
            }
            return taskRepository.save(fetchedUser); // Save the user who assigned the task
        } else {
            throw new UserAlreadyExistsException("User does not exist for the given email ID");
        }
    }


    @Override
    public UserDetails updateTaskToUser(Task task, String userEmail) throws TaskNotFoundException, UserNotFoundException {
        // Update the specific track details
        boolean flag = false;
        if (taskRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException("");
        }

        UserDetails user = taskRepository.findById(userEmail).get();
        List<Task> taskList = user.getTaskList();

        for (Task userTask : taskList) {
            if (userTask.getTaskId().equals(task.getTaskId())) {
                // Compare each attribute and update if necessary
                if (!userTask.equals(task)) {
                    userTask.setTaskName(task.getTaskName());
                    userTask.setTaskDescription(task.getTaskDescription());
                    userTask.setAssignTo(task.getAssignTo());
                    userTask.setDueDate(task.getDueDate());
                    userTask.setPriority(task.getPriority());
                    userTask.setStatus(task.getStatus());

                    // Update the task in all users' data, including admin
                    List<UserDetails> allUsers = taskRepository.findAll();
                    for (UserDetails userDetails : allUsers) {
                        List<Task> allTasks = userDetails.getTaskList();
                        for (Task userTaskToUpdate : allTasks) {
                            if (userTaskToUpdate.getTaskId().equals(task.getTaskId())) {
                                // Update task attributes for all users
                                userTaskToUpdate.setTaskName(task.getTaskName());
                                userTaskToUpdate.setTaskDescription(task.getTaskDescription());
                                userTaskToUpdate.setAssignTo(task.getAssignTo());
                                userTaskToUpdate.setDueDate(task.getDueDate());
                                userTaskToUpdate.setPriority(task.getPriority());
                                userTaskToUpdate.setStatus(task.getStatus());
                            }
                        }
                        userDetails.setTaskList(allTasks);
                        taskRepository.save(userDetails);
                    }
                    flag = true;
                }
            }
        }

        if (!flag) {
            throw new TaskNotFoundException("");
        }

        user.setTaskList(taskList);
        return taskRepository.save(user);
    }






    @Override
    public UserDetails deleteUser(String emailId) throws UserNotFoundException {
        Optional<UserDetails> userOptional = taskRepository.findById(emailId);
        if (userOptional.isPresent()) {
            UserDetails userToDelete = userOptional.get();
            taskRepository.delete(userToDelete);
            return userToDelete;
        } else {
            throw new UserNotFoundException("User with the specified ID not found");
        }
    }
    @Override
    public UserDetails deleteTask(Task task, String emailId) throws TaskNotFoundException{
        Optional<UserDetails> userOptional=taskRepository.findById(emailId);
        if(userOptional.isPresent()){
            UserDetails fetchedUser=userOptional.get();
            List<Task> taskList=fetchedUser.getTaskList();
            if(taskList==null || taskList.isEmpty() ) {
                throw new TaskNotFoundException("TaskList with Specified Id is not Found");
            }
            else{
                boolean taskPresent=false;
                for(Task existingTask:taskList) {
                    if (existingTask.getTaskId() == task.getTaskId()) {
                        taskList.remove(existingTask);
                        fetchedUser.setTaskList(taskList);
                        taskPresent = true;
                        break;
                    }
                }
                if(taskPresent==false){
                    throw new TaskNotFoundException("Task with Specified Id is not Found ");

                }
            }
            return taskRepository.save(fetchedUser);
        }
        else {
            throw new UserNotFoundException("user with the specified Id not found");
        }
    }
    @Override
    public String getUserName(String emailID)throws UserNotFoundException {
        Optional<UserDetails >fetchedUser=taskRepository.findById(emailID);
        if (fetchedUser.isEmpty()) {
            throw new UserNotFoundException("user with the specified Id not found");
        }
        return fetchedUser.get().getUserName();
    }
    @Override
    public List<UserDetails> getAllUser(String emailId) throws UserNotFoundException{
        Optional<UserDetails >fetchedUser=taskRepository.findById(emailId);
        if (fetchedUser.isEmpty()) {
            throw new UserNotFoundException("user with the specified Id not found");
        }
        return taskRepository.findAll();
    }

    @Override
    public String getUserRole(String emailId)throws UserNotFoundException {
        if (taskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException("user with the specified Id not found");
        }
        return taskRepository.findById(emailId).get().getRole();
    }


}
