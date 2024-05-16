package com.niit.project.userauthenticationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.project.userauthenticationservice.domain.UserDetails;
import com.niit.project.userauthenticationservice.exception.UserAlreadyExistsException;
import com.niit.project.userauthenticationservice.security.JWTSecurityTokenGenerator;
import com.niit.project.userauthenticationservice.services.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private JWTSecurityTokenGenerator tokenGenerator;
    @Mock
    private UserServiceImpl userService;
    private UserDetails user;

    @InjectMocks
    UserController userController;
    @BeforeEach
    void setUp() {
        user = new UserDetails("sharath@gmail.com","admin","123");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void givenUserToSaveReturnUserSuccess() throws Exception {
        when(userService.registerUser(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user)))
                .andExpect(status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void givenUserToSaveReturnUserFailure() throws Exception {
        when(userService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
    private static String asJSONString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
