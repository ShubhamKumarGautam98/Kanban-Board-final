package com.niit.project.boardtaskservice.service;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.TaskNotFoundException;
import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;
import com.niit.project.boardtaskservice.repository.ITaskRepository;
import com.niit.project.boardtaskservice.services.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private UserDetails user;
    private Task task1,task2;
    @BeforeEach
    void setUp() {
        user = new UserDetails("shubham@gmail.com", "User", "password", "user", new ArrayList<>());
        task1 = new Task(1, "Project", "Creating", "2024-02-22", "shubham", "In Progress");
        task2 = new Task(2, "Project2", "Creating2", "2024-02-23", "shubham", "In Progress");
        user.setTaskList(Arrays.asList(task1, task2));
    }
    @Test
    void getAllTaskFromUser_UserExistsWithTasks_ReturnsTaskList() throws TaskNotFoundException, UserNotFoundException {
        String emailId = "hinata@gmail.com.com";
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(new Task());
        expectedTaskList.add(new Task());
        UserDetails userWithTasks = new UserDetails();
        userWithTasks.setEmailId(emailId);
        userWithTasks.setTaskList(expectedTaskList);
        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userWithTasks));

        List<Task> actualTaskList = userService.getAllTaskFromUser(emailId);

        verify(taskRepository, times(1)).findById(emailId);

        assertEquals(expectedTaskList, actualTaskList);
    }
    @Test
    void getAllTaskFromUser_UserNotFound_ThrowsUserNotFoundException() {
        String emailId = "Nami@gmail.com";
        when(taskRepository.findById(emailId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getAllTaskFromUser(emailId));

        verify(taskRepository, times(1)).findById(emailId);
    }
    @Test
    void getAllTaskFromUser_UserExistsWithoutTasks_ThrowsTaskNotFoundException() {
        String emailId = "sharath@gmail.com";
        UserDetails userWithoutTasks = new UserDetails();
        userWithoutTasks.setEmailId(emailId);
        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userWithoutTasks));

        assertThrows(TaskNotFoundException.class, () -> userService.getAllTaskFromUser(emailId));

        verify(taskRepository, times(1)).findById(emailId);
    }
    @Test
    void getOneTaskById_TaskNotFound_ThrowsTaskNotFoundException() {
        String emailId = "shubham@gmail.com";
        int taskId = 123;
        UserDetails userWithTasks = new UserDetails();
        userWithTasks.setEmailId(emailId);
        List<Task> taskList = new ArrayList<>();
        userWithTasks.setTaskList(taskList);
        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userWithTasks));
        assertThrows(TaskNotFoundException.class, () -> userService.getOneTaskById(taskId, emailId));
        verify(taskRepository, times(1)).findById(emailId);
    }
    @Test
    void getOneTaskById_UserNotFound_ThrowsUserNotFoundException() {
        String emailId = "robin@gmail.com";
        int taskId = 123;
        when(taskRepository.findById(emailId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getOneTaskById(taskId, emailId));
        verify(taskRepository, times(1)).findById(emailId);
    }

    @Test
    void getOneTaskById_TaskFound_ReturnsTask() throws TaskNotFoundException, UserNotFoundException {
        String emailId = "test@example.com";
        int taskId = 123;
        Task expectedTask = new Task();
        expectedTask.setTaskId(taskId);
        UserDetails userWithTasks = new UserDetails();
        userWithTasks.setEmailId(emailId);
        List<Task> taskList = new ArrayList<>();
        taskList.add(expectedTask);
        userWithTasks.setTaskList(taskList);
        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userWithTasks));
        Task actualTask = userService.getOneTaskById(taskId, emailId);
        verify(taskRepository, times(1)).findById(emailId);
        assertEquals(expectedTask, actualTask);
    }

}
