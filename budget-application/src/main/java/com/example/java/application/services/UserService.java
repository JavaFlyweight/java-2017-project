package com.example.java.application.services;

import java.util.UUID;

import com.example.java.domain.model.User;

public interface UserService {
	public UUID registerUser(String name, String lastName, String email, String password, String image);

	public void validateCredentials(String email, String password);

	public User getUser(UUID id);

	public User getUser(String email);

	public boolean isUserExist(String email);
        
        public String getLoggedUserLogin();
}
