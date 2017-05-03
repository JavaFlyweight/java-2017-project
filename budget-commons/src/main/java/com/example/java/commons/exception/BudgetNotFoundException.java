package com.example.java.commons.exception;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BudgetNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetNotFoundException.class);

    public BudgetNotFoundException(UUID budgetId) {
        super("Could not find budget '" + budgetId + "'.");
        LOGGER.error("Could not find budget '" + budgetId + "'.");
    }

    public BudgetNotFoundException() {
        super("Could not find budget.");
        LOGGER.error("Could not find budget.");
    }
}
