package com.example.java.commons.exception;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyHasBudget extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAlreadyHasBudget.class);

    public UserAlreadyHasBudget(UUID budgetId) {
        super("User already has budget with id '" + budgetId + "'.");
        LOGGER.error("User already has budget with id '" + budgetId + "'.");
    }
}
