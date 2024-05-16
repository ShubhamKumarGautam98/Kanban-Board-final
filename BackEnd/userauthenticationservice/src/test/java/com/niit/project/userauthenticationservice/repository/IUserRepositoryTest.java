package com.niit.project.userauthenticationservice.repository;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class IUserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    UserDetails user1,user2;

    @BeforeEach
    void SetUp(){
        user1=new UserDetails("Sharath@gmail.com","admin","123");
        user2=new UserDetails("anandhapad@gmail.com","user","1234");
    }

    @AfterEach
    void tearDown(){
        user1=null;
        user2=null;
        userRepository.deleteAll();
    }


    @Test
    void saveUser() {
        userRepository.save(user1);
        UserDetails userinfo=userRepository.findById(user1.getEmailId()).get();
        assertEquals(user1.getEmailId(), userinfo.getEmailId());
    }
    @Test
    void getAllUserSuccess(){
        userRepository.save(user1);
        userRepository.save(user2);
        List<UserDetails> userList=userRepository.findAll();
        assertEquals(2,userList.size());
        assertFalse(userList.isEmpty());
    }

    @Test
    void getAllUserFail(){

        List<UserDetails> userList=userRepository.findAll();
        assertEquals(0,userList.size());
        assertTrue(userList.isEmpty());
    }
    @Test
    void getOneUserSuccess(){
        userRepository.save(user1);
        userRepository.save(user2);
        Optional<UserDetails> userOptional=userRepository.findById("anandhapad@gmail.com");
        assertTrue(userOptional.isPresent());
        assertEquals(user2.getEmailId(),userOptional.get().getEmailId());
    }

    @Test
    void getOneUserFail(){
        userRepository.save(user1);
        userRepository.save(user2);
        Optional<UserDetails> userOptional=userRepository.findById("kavin@gmail.com");
        assertFalse(userOptional.isPresent());
    }


    @Test
    void deleteUserByEmailIdSuccess(){
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.deleteById("anandh@gmail.com");
        Optional<UserDetails> userOptional=userRepository.findById("anandh@gmail.com");
        assertFalse(userOptional.isPresent());
    }
    @Test
    void deleteUserByEmailIdFail(){
        userRepository.save(user1);
        userRepository.save(user2);
        Optional<UserDetails> userOptional=userRepository.findById("anandh@gmail.com");
        assertTrue(userOptional.isPresent());
    }

}