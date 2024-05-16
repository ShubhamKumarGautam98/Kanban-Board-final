import com.niit.project.boardtaskservice.domain.Task;
import com.niit.project.boardtaskservice.domain.UserDetails;
import com.niit.project.boardtaskservice.exceptions.TaskAlreadyExistsException;
import com.niit.project.boardtaskservice.exceptions.TaskNotFoundException;
import com.niit.project.boardtaskservice.exceptions.UserAlreadyExistsException;
import com.niit.project.boardtaskservice.exceptions.UserNotFoundException;
import com.niit.project.boardtaskservice.repository.ITaskRepository;
import com.niit.project.boardtaskservice.proxy.TaskProxy;
import com.niit.project.boardtaskservice.services.AdminServiceImpl;
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
//@ExtendWith(MockitoExtension.class)
//class AdminServiceTest {
   // private Task task1,task2;
//    @BeforeEach
//    void setUp() {
//        user = new UserDetails("shubham@gmail.com", "User", "password", "user", new ArrayList<>());
//        task1 = new Task(1, "Project", "Creating", "2024-02-22", "shubham", "In Progress");
//        task2 = new Task(2, "Project2", "Creating2", "2024-02-23", "shubham", "In Progress");
//        user.setTaskList(Arrays.asList(task1, task2));
//    }
//
//    @Test
//    void deleteTask_Success() throws TaskNotFoundException, UserNotFoundException {
//        UserDetails mockedUser = mock(UserDetails.class);
//        Task taskToDelete = new Task(1, "TaskToDelete", "Description", "2024-02-22", "AssignTo", "Status");
//        List<Task> taskList = new ArrayList<>();
//        taskList.add(taskToDelete);
//        when(taskRepository.findById(anyString())).thenReturn(Optional.of(mockedUser));
//        when(mockedUser.getTaskList()).thenReturn(taskList);
//        when(taskRepository.save(any())).thenReturn(mockedUser);
//        UserDetails updatedUser = adminService.deleteTask(taskToDelete, "anyEmailId");
//        verify(taskRepository, times(1)).findById("anyEmailId");
//        verify(taskRepository, times(1)).save(mockedUser);
//        assertNotNull(updatedUser);
//        assertEquals(mockedUser, updatedUser);
//        assertFalse(updatedUser.getTaskList().contains(taskToDelete));
//    }
//
//
//
//    @Test
//    void getUserName_UserFound_ReturnsUserName() throws UserNotFoundException {
//        String emailId = "shubham@gmail.com";
//        String expectedUserName = "Test User";
//        UserDetails userDetails = new UserDetails();
//        userDetails.setEmailId(emailId);
//        userDetails.setUserName(expectedUserName);
//        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userDetails));
//        String actualUserName = adminService.getUserName(emailId);
//        verify(taskRepository, times(1)).findById(emailId);
//        assertEquals(expectedUserName, actualUserName);
//    }
//    @Test
//    void getUserName_UserNotFound_ThrowsUserNotFoundException() {
//        String emailId = "robin@gmial.com";
//        when(taskRepository.findById(emailId)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> adminService.getUserName(emailId));
//        verify(taskRepository, times(1)).findById(emailId);
//    }
//
//    @Test
//    void registerUser_Success() throws UserAlreadyExistsException {
//        when(taskRepository.findById(user.getEmailId())).thenReturn(Optional.empty());
//        when(taskRepository.save(user)).thenReturn(user);
//
//        UserDetails savedUser = adminService.registerUser(user);
//
//        assertNotNull(savedUser);
//        assertEquals(user, savedUser);
//        verify(taskProxy, times(1)).registerUser(user);
//    }
//    @Test
//    void registerUser_UserAlreadyExists() {
//        when(taskRepository.findById(user.getEmailId())).thenReturn(Optional.of(user));
//
//        assertThrows(UserAlreadyExistsException.class, () -> adminService.registerUser(user));
//
//        verify(taskRepository, never()).save(any());
//        verify(taskProxy, never()).registerUser(any());
//    }
//    @Test
//    void saveTaskToUser_Success() throws TaskAlreadyExistsException, UserAlreadyExistsException {
//        UserDetails mockedUser = mock(UserDetails.class);
//
//        List<Task> taskList = new ArrayList<>();
//        when(mockedUser.getTaskList()).thenReturn(taskList);
//        when(taskRepository.findById(anyString())).thenReturn(Optional.of(mockedUser));
//
//        UserDetails updatedUser = adminService.saveTaskToUser(task1, "anyEmailId");
//    }
//    @Test
//    void updateTaskToUser_Success() throws TaskNotFoundException, UserNotFoundException {
//        when(taskRepository.findById(user.getEmailId())).thenReturn(Optional.of(user));
//        when(taskRepository.save(any())).thenReturn(user);
//
//        UserDetails updatedUser = adminService.updateTaskToUser(task1, user.getEmailId());
//
//        assertNotNull(updatedUser);
//        assertTrue(updatedUser.getTaskList().contains(task1));
//        verify(taskRepository, times(1)).save(user);
//    }
//    @Test
//    void getUserRole_UserFound_ReturnsRole() throws UserNotFoundException {
//        // Mock data
//        String emailId = "sharath@gmail.com";
//        String role = "admin";
//        UserDetails userDetails = new UserDetails();
//        userDetails.setRole(role);
//        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userDetails));
//        String actualRole = adminService.getUserRole(emailId);
//        verify(taskRepository, times(2)).findById(emailId);
//        assertEquals(role, actualRole);
//    }
//
//
//
//
//    @Test
//    void getUserRole_UserNotFound_ThrowsUserNotFoundException() {
//        String emailId = "nonexistent@example.com";
//        when(taskRepository.findById(emailId)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> adminService.getUserRole(emailId));
//        verify(taskRepository, times(1)).findById(emailId);
//    }
//    @Test
//    void getAllUser_UserFound_ReturnsAllUsers() throws UserNotFoundException {
//        String emailId = "sharath@gmail.com";
//        UserDetails userDetails = new UserDetails();
//        List<UserDetails> userList = new ArrayList<>();
//        userList.add(userDetails);
//        when(taskRepository.findById(emailId)).thenReturn(Optional.of(userDetails));
//        when(taskRepository.findAll()).thenReturn(userList);
//        List<UserDetails> actualUsers = adminService.getAllUser(emailId);
//        verify(taskRepository, times(1)).findById(emailId);
//        verify(taskRepository, times(1)).findAll();
//        assertEquals(userList, actualUsers);
//    }
//    @Test
//    void getAllUser_UserNotFound_ThrowsUserNotFoundException() {
//        String emailId = "king@gmail.com";
//        when(taskRepository.findById(emailId)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> adminService.getAllUser(emailId));
//        verify(taskRepository, times(1)).findById(emailId);
//        verify(taskRepository, never()).findAll();
//    }
//}