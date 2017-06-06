package com.example.java.commons.exceptions;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BudgetForbiddenAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetForbiddenAccessException.class);

    public BudgetForbiddenAccessException(UUID budgetId) {
        super("User does not have enougth permissions to get budget '" + budgetId + "'.");
        LOGGER.error("User does not have enougth permissions to get '" + budgetId + "'.");
    }
}
