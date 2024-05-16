package com.niit.project.userauthenticationservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserDetails {
    @Id
    private String emailId;

    private  String role;
    private String password;

    public UserDetails() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails(String emailId, String role, String password) {
        this.emailId = emailId;
        this.role = role;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "emailId='" + emailId + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
