package com.example.java.commons.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoggedUserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedUserNotFoundException.class);


    public LoggedUserNotFoundException() {
        super("Could not find logged user.");
        LOGGER.error("Could not find logged user.");
    }
}
