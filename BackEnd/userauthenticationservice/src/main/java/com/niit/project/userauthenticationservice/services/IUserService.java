package com.niit.project.userauthenticationservice.services;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import com.niit.project.userauthenticationservice.exception.UserAlreadyExistsException;
import com.niit.project.userauthenticationservice.exception.UserLoginException;

public interface IUserService {
    UserDetails registerUser(UserDetails user)throws UserAlreadyExistsException;
    UserDetails loginUser(UserDetails user)throws UserLoginException;
    UserDetails updatePassword(UserDetails user)throws  UserLoginException ;
}
