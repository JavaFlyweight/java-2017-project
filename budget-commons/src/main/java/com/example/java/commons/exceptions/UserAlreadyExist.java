package com.example.java.commons.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExist extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAlreadyExist.class);


    public UserAlreadyExist(String email) {
        super("User with email " + email + " alredy exist.");
        LOGGER.error("User with email " + email + " alredy exist.");
    }
}
