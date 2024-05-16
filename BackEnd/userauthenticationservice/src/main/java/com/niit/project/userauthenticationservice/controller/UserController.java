package com.niit.project.userauthenticationservice.controller;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import com.niit.project.userauthenticationservice.exception.UserAlreadyExistsException;
import com.niit.project.userauthenticationservice.exception.UserLoginException;
import com.niit.project.userauthenticationservice.security.ISecurityTokenGenerator;
import com.niit.project.userauthenticationservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("/api/v1")
public class UserController {
    private final IUserService userService;
    private final ISecurityTokenGenerator tokenGenerator;

    @Autowired
    public UserController(IUserService userService, ISecurityTokenGenerator tokenGenerator) {
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDetails user) {
        ResponseEntity responseEntity;
        try {
            UserDetails saveduser = userService.registerUser(user);
            responseEntity = new ResponseEntity<>(saveduser, HttpStatus.ACCEPTED);
        } catch (UserAlreadyExistsException exception) {
            responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDetails user){
        ResponseEntity responseEntity;
        try{
            UserDetails fetchedUser=userService.loginUser(user);
           Map<String,String> token=tokenGenerator.generateToken(fetchedUser);

            responseEntity=new ResponseEntity<>(token, HttpStatus.FOUND);
        }catch (UserLoginException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("/passwordChange")
    public ResponseEntity<?> updatePassword(@RequestBody UserDetails user){
        ResponseEntity responseEntity;
        try{
            UserDetails changedPassword=userService.updatePassword(user);
            responseEntity=new ResponseEntity<>(changedPassword, HttpStatus.CREATED);
        }catch (UserLoginException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}