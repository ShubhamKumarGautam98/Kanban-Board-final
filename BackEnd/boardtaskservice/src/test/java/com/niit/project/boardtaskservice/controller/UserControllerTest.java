package com.niit.project.boardtaskservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.UserAlreadyExistsException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;


    @InjectMocks
    private UserController userController;

    UserDetails user1 ,user2;

    @BeforeEach
    void setUp() {
        user1=new UserDetails("sharath@gmail.com","sharath","124571","Admin",null);
        user2=new UserDetails("shubham@gmail.com","shubham","1241","User",null);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() {
        user1=null;
        user2=null;
    }

    private static String asJSONString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
