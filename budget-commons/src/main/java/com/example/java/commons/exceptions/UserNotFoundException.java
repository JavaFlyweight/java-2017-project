package com.example.java.commons.exceptions;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);


    public UserNotFoundException(String email) {
        super("Could not find user with email'" + email + "'.");
        LOGGER.error("Could not find user with email'" + email + "'.");
    }


    public UserNotFoundException() {
        super("Could not find user.");
        LOGGER.error("Could not find user.");
    }
}
