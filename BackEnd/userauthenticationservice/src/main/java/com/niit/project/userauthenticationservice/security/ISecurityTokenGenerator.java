package com.niit.project.userauthenticationservice.security;

import com.niit.project.userauthenticationservice.domain.UserDetails;

import java.util.Map;

public interface ISecurityTokenGenerator {
    public Map<String,String> generateToken(UserDetails userDetails);

}
