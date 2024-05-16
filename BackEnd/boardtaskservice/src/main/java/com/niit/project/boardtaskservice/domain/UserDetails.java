package com.niit.project.boardtaskservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserDetails {
    @Id
    private String emailId;
    private String userName;
    @Transient
    private String password;
    private  String role="user";
    List<Task> taskList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserDetails() {
    }

    public UserDetails(String emailId, String userName, String password, String role, List<Task> taskList) {
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.taskList = taskList;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "emailId='" + emailId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}