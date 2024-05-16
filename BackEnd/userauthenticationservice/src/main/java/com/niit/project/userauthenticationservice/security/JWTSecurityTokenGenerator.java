package com.niit.project.userauthenticationservice.security;

import com.niit.project.userauthenticationservice.domain.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JWTSecurityTokenGenerator implements ISecurityTokenGenerator {

    @Override
    public Map<String, String> generateToken(UserDetails userDetails) {
        String token = Jwts.builder()
                .setSubject(userDetails.getEmailId())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "SecretKey").compact();

        Map<String, String> tokenMessage = new HashMap<>();
        tokenMessage.put("token", token);
        tokenMessage.put("message", "user logged in successfully");
        tokenMessage.put("userData : ", userDetails.toString());
        return tokenMessage;
    }
}
