package com.example.java.application.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.commons.exceptions.LoggedUserNotFoundException;
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
    public UUID registerUser(String name, String lastName, String email, String password, String image) {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // TODO Must throw custom exception (isEmpty or ==null??).
        }

        if (isUserExist(email)) {
            // TODO Must throw user alredy exist exception.
        }

        User newUser = userRepository.save(new User(name, lastName, email, password, image));

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
    public String getLoggedUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userLogin = null;
        if (principal instanceof com.example.java.domain.model.SecurityUserDetails) {
            com.example.java.domain.model.SecurityUserDetails loggedUser = (com.example.java.domain.model.SecurityUserDetails) principal;
            userLogin = loggedUser.getUsername();
        }
        LOGGER.info("Logged user is  {}", new Object[]{userLogin});
        if (userLogin == null) {
            throw new LoggedUserNotFoundException();
        }
        return userLogin;
    }
}
