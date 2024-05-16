package com.niit.project.boardtaskservice.controller;

import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.InvalidStatusChangeException;
import com.niit.project.boardtaskservice.exceptions.TaskNotFoundException;
import com.niit.project.boardtaskservice.exceptions.UserAlreadyExistsException;
import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;
import com.niit.project.boardtaskservice.services.IAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2")
public class AdminController {
    private final IAdminService adminService;



    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;

    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDetails userDetails) {
        ResponseEntity responseEntity;
        try {
            UserDetails saveduser = adminService.registerUser(userDetails);
            responseEntity = new ResponseEntity<>(saveduser, HttpStatus.ACCEPTED);
        } catch (UserAlreadyExistsException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTaskToUser(@RequestBody Task task, HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            responseEntity = new ResponseEntity<>(adminService.updateTaskToUser(task, userId), HttpStatus.ACCEPTED);
        } catch (TaskNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }catch (InvalidStatusChangeException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTaskToUser(@RequestBody Task task, HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            responseEntity = new ResponseEntity<>(adminService.deleteTask(task, userId), HttpStatus.OK);
        } catch (TaskNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("/userName")
    public ResponseEntity<?> getUserName(HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            String userName = adminService.getUserName(userId);
            responseEntity = new ResponseEntity<>(userName, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUser(HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            List<UserDetails> users = adminService.getAllUser(userId);
            responseEntity = new ResponseEntity<>(users, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/userRole")
    public ResponseEntity<?> getUserRole(HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            String userRole = adminService.getUserRole(userId);
            responseEntity = new ResponseEntity<>(userRole, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/saveTaskToUser")
    public ResponseEntity<?> saveTaskToUser(@RequestBody Task task, HttpServletRequest request) {
        ResponseEntity<?> responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);

            // Generate a new UUID if taskId is null
            if (task.getTaskId() == null) {
                task.setTaskId(UUID.randomUUID());
            }

            responseEntity = new ResponseEntity<>(adminService.saveTaskToUser(task, userId), HttpStatus.ACCEPTED);
        } catch (TaskNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            responseEntity = new ResponseEntity<>(adminService.deleteUser(userId), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    private String getCustomerIdFromClaims(HttpServletRequest request) {
        String userId=request.getAttribute("emailId").toString();
        return userId;
    }
}
