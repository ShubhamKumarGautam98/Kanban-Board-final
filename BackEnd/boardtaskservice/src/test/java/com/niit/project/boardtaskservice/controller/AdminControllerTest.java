package com.niit.project.boardtaskservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.UserAlreadyExistsException;
import com.niit.project.boardtaskservice.services.AdminServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminServiceImpl adminService;

    @InjectMocks
    private AdminController adminController;

    UserDetails user1, user2;
    Task task1, task2;
    List<Task> taskList;
    List<UserDetails> userList;

    @BeforeEach
    void setUp() {
        task1 = new Task(1, "KanbanBoard", "MajorProject", "12-03-2024", "anandha", "To do");
        task2 = new Task(2, "KanbanBoard2", "MajorProject", "13-03-2024", "Shubham", "To do");
        taskList = Arrays.asList(task1, task2);

        user1 = new UserDetails("sharath@gmail.com", "sharath", "124571", "Admin", taskList);
        user2 = new UserDetails("shubham@gmail.com", "shubham", "1241", "User", taskList);
        userList = Arrays.asList(user1, user2);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        user2 = null;
//        taskList = null;
        userList = null;
    }
    @Test
    void registerUserSuccess() throws Exception {
        Mockito.when(adminService.registerUser(any())).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user1)))
                .andExpect(status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
        verify(adminService,times(1)).registerUser(any());
    }
    @Test
    public void registerUserFailure() throws Exception {
        Mockito.when(adminService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJSONString(user1)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(adminService,times(1)).registerUser(any());
    }


    private static String asJSONString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}