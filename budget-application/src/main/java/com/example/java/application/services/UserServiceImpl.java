package com.example.java.application.services;

import com.example.java.commons.exception.LoggedUserNotFoundException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.domain.model.User;
import com.example.java.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateCredentials(String email, String password) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUser(UUID id) {
        User user = userRepository.findOneById(id);

        if (user == null) {
            // TODO Must throw custom exception.
        }

        return user;
    }

    @Override
    public User getUser(String email) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            // TODO Must throw custom exception.
        }

        return user;
    }

    @Override
    public UUID registerUser(String name, String lastName, String email, String password) {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // TODO Must throw custom exception (isEmpty or ==null??).
        }

        if (isUserExist(email)) {
            // TODO Must throw user alredy exist exception.
        }

        User newUser = userRepository.save(new User(name, lastName, email, password));

        return newUser.getId();
    }

    @Override
    public boolean isUserExist(String email) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            return false;
        }

        return true;
    }

    @Override
    public String getLoggedUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user;
        if (principal instanceof User) {
            org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) principal;
            user =loggedUser.getUsername();
        } else {
            user =principal.toString();
        }
        if(user!=null){
            throw  new LoggedUserNotFoundException();
        }
        return user;
    }
}
