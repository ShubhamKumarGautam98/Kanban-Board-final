package com.niit.project.userauthenticationservice.services;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import com.niit.project.userauthenticationservice.exception.UserAlreadyExistsException;
import com.niit.project.userauthenticationservice.exception.UserLoginException;
import com.niit.project.userauthenticationservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
    private final IUserRepository userRepository;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails registerUser(UserDetails user) throws UserAlreadyExistsException{
        Optional<UserDetails> userDetails=userRepository.findById(user.getEmailId());
        if(userDetails.isPresent()){
            throw new UserAlreadyExistsException("User already present in given emailId");
        }
        else{
            return userRepository.save(user);
        }
    }

    @Override
    public UserDetails loginUser(UserDetails user) throws UserLoginException {

        Optional<UserDetails> userDetails=userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        if(userDetails.isPresent()){
            return userDetails.get();
        }
        else{
            throw new UserLoginException("User EmailId And Password Not Exists");

        }
    }

    @Override
    public UserDetails updatePassword(UserDetails user)throws  UserLoginException {
        Optional<UserDetails> userOptional = userRepository.findById(user.getEmailId());
        if (userOptional.isPresent())
        {
            UserDetails existingUser = userOptional.get();
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new  UserLoginException("User EmailId And Password Not Exists");
        }
    }




}