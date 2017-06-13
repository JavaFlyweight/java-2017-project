package com.example.java.commons.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(WrongCredentialsException.class);


    public WrongCredentialsException() {
        super("Wrong login credentials.");
        LOGGER.error("Wrong login credentials.");
    }
}
