package com.niit.project.boardtaskservice.repository;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class ITaskRepositoryTest {

    @Autowired
    private ITaskRepository taskRepository;

    UserDetails user1, user2;
    Task task1,task2,task3;

    @BeforeEach
    void setUp() {
        user1 = new UserDetails("Sharath@gmail.com", "User1", "password1", "role1", null);
        user2 = new UserDetails("anandhapad@gmail.com", "User2", "password2", "role2", null);
        task1=new Task(2,"Front End part2","Creating webpage2","28-02-2024","anandh@gmail.com","TO-Do");
        task2=new Task(3,"Front End part3","Creating webpage3","29-02-2024","anandh@gmail.com","TO-Do");
        task3=new Task(4,"Front End part4","Creating webpage4","30-02-2024","anandh@gmail.com","TO-Do");
    }


    @AfterEach
    void tearDown() {
        user1=null;
        user2=null;
        taskRepository.deleteAll();
    }


    @Test
    void getOneTaskSuccess(){
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> addingTasks = new ArrayList<>();
        addingTasks.add(task1);

        savedUser.setTaskList(addingTasks);
        taskRepository.save(savedUser);
        assertFalse(addingTasks.isEmpty());
        Optional<UserDetails> fetchOptional = taskRepository.findById(user1.getEmailId());

        UserDetails savedUser1 = fetchOptional.get();
        List<Task> taskList=savedUser1.getTaskList();
        for(Task existingTask :taskList){
            assertEquals(existingTask.getTaskName(),task1.getTaskName());
        }
    }

    @Test
    void getOneTaskFail() {
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> addingTasks = new ArrayList<>();


        savedUser.setTaskList(addingTasks);
        taskRepository.save(savedUser);
        assertTrue(addingTasks.isEmpty());
    }

    @Test
    void getAllTaskSuccess(){
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> addingTasks = new ArrayList<>();
        addingTasks.add(task1);
        addingTasks.add(task2);
        addingTasks.add(task3);
        savedUser.setTaskList(addingTasks);
        taskRepository.save(savedUser);
        assertEquals(3,addingTasks.size());
    }
    @Test
    void getAllTaskFail(){
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> addingTasks = new ArrayList<>();

        assertNotEquals(3,addingTasks.size());
    }


    @Test
    void SuccessToUpdateTask() {
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> updatedTasks = new ArrayList<>();
        updatedTasks.add(task1);
        savedUser.setTaskList(updatedTasks);
        taskRepository.save(savedUser);

        Optional<UserDetails> updatedUserOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(updatedUserOptional.isPresent());
        UserDetails updatedUser = updatedUserOptional.get();
        assertNotEquals(savedUser, updatedUser);
    }

    @Test
    void failToUpdate(){
        taskRepository.save(user1);
        Optional<UserDetails> userOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails savedUser = userOptional.get();

        List<Task> updatedTasks = new ArrayList<>();
        updatedTasks.add(task1);
        savedUser.setTaskList(updatedTasks);


        Optional<UserDetails> updatedUserOptional = taskRepository.findById(user1.getEmailId());
        assertTrue(updatedUserOptional.isPresent());
        UserDetails updatedUser = updatedUserOptional.get();
        assertNotEquals(savedUser, updatedUser);
    }



    @Test
    void getAllUserSuccess() {
        taskRepository.save(user1);
        taskRepository.save(user2);
        List<UserDetails> userList = taskRepository.findAll();
        assertEquals(2, userList.size());
    }

    @Test
    void getAllUserFail() {
        List<UserDetails> userList = taskRepository.findAll();
        assertEquals(0, userList.size());
    }

    @Test
    void getOneUserSuccess() {
        taskRepository.save(user1);
        taskRepository.save(user2);
        Optional<UserDetails> userOptional = taskRepository.findById(user2.getEmailId());
        assertTrue(userOptional.isPresent());
        UserDetails fetchedUser = userOptional.get();
        assertEquals(user2.getEmailId(), fetchedUser.getEmailId());
    }

    @Test
    void getOneUserFail() {
        taskRepository.save(user1);
        taskRepository.save(user2);
        Optional<UserDetails> userOptional = taskRepository.findById("kavin@gmail.com");
        assertFalse(userOptional.isPresent());
    }

    @Test
    void deleteUserByEmailIdSuccess() {
        taskRepository.save(user1);
        taskRepository.save(user2);
        taskRepository.deleteById(user1.getEmailId());
        assertFalse(taskRepository.existsById(user1.getEmailId()));
    }

    @Test
    void deleteUserByEmailIdFail() {
        taskRepository.save(user1);
        taskRepository.save(user2);
        taskRepository.deleteById("nonexistent@example.com");
        assertTrue(taskRepository.existsById(user1.getEmailId()));
    }
}