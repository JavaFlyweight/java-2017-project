package com.example.java.application.services;

import java.util.UUID;

import com.example.java.domain.model.User;
import com.example.java.domain.model.UserCreateRequest;

public interface UserService {

    public User registerUser(UserCreateRequest userCreateRequest);


    public void validateCredentials(User user, String password);


    public User getUser(UUID id);


    public User getUser(String email, String password);


    public User updateUser(UserCreateRequest userCreateRequest, User actualUser);


    public boolean isUserExist(String email);


    public String getLoggedUserLogin();


    public String[] getAuthorizationCredentials(String authorization);


    public User removeUser(User user);
}
