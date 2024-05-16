//package com.niit.project.userauthenticationservice.service;
//
//import com.niit.project.userauthenticationservice.domain.UserDetails;
//import com.niit.project.userauthenticationservice.exception.UserAlreadyExistsException;
//import com.niit.project.userauthenticationservice.exception.UserLoginException;
//import com.niit.project.userauthenticationservice.repository.IUserRepository;
//import com.niit.project.userauthenticationservice.services.IUserService;
//import com.niit.project.userauthenticationservice.services.UserServiceImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private IUserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private UserDetails user;
//
//    @BeforeEach
//    public void setUp() {
//        user = new UserDetails("shubham@gmail.com", "111");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        user = null;
//    }
//
//    @Test
//    public void givenUserToSaveReturnSavedUserSuccess() throws UserAlreadyExistsException {
//        when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
//        when(userRepository.save(any())).thenReturn(user);
//
//        assertEquals(user, userService.registerUser(user));
//
//        verify(userRepository, times(1)).findById(any());
//        verify(userRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void givenUserToSaveReturnSavedUserFailure() throws UserAlreadyExistsException {
//        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
//
//        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
//
//        verify(userRepository, times(1)).findById(any());
//        verify(userRepository, times(0)).save(any());
//    }
//    @Test
//    public void givenUserLoginSuccessReturnRetrievedUser() {
//        when(userRepository.findByEmailIdAndPassword(any(), any())).thenReturn(Optional.of(user));
//
//        assertEquals(user, userService.loginUser(user));
//
//        verify(userRepository, times(1)).findByEmailIdAndPassword(any(), any());
//    }
//    @Test
//    void givenUserLoginFailureThrowUserLoginException() {
//        when(userRepository.findByEmailIdAndPassword(any(), any())).thenReturn(Optional.empty());
//        assertThrows(UserLoginException.class, () -> userService.loginUser(user));
//        verify(userRepository, times(1)).findByEmailIdAndPassword(any(), any());
//    }
//
//}
