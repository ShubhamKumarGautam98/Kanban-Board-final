package com.niit.project.boardtaskservice.controller;

import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;
import com.niit.project.boardtaskservice.services.IUserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController

@RequestMapping("/api/v3")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getOneTask/{taskId}")
    public ResponseEntity<?> getATask(@PathVariable String taskId, HttpServletRequest request) {
        ResponseEntity<?> responseEntity;
        try {
            // Convert the taskId String to UUID
            UUID uuidTaskId = UUID.fromString(taskId);

            System.out.println("header: " + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            System.out.println("User ID from claims: " + userId);
            responseEntity = new ResponseEntity<>(userService.getOneTaskById(uuidTaskId, userId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            responseEntity = new ResponseEntity<>("Invalid taskId format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Cannot get a task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @GetMapping("/getAllTask")
    public ResponseEntity<?> getAllTaskFromUser(HttpServletRequest request) {
        ResponseEntity responseEntity;
        try {
            String userId = getCustomerIdFromClaims(request);
            responseEntity = new ResponseEntity<>(userService.getAllTaskFromUser(userId), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }return responseEntity;
    }



    private String getCustomerIdFromClaims(HttpServletRequest request) {
        String userId=request.getAttribute("emailId").toString();
        return userId;
    }

}
