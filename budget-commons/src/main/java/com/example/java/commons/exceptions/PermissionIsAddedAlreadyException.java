package com.example.java.commons.exceptions;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermissionIsAddedAlreadyException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionIsAddedAlreadyException.class);

    public PermissionIsAddedAlreadyException(String userLogin, UUID budgetId) {
        super("This usuer "+userLogin+" has this permission already for budget '" + budgetId + "'.");
        LOGGER.error("This usuer "+userLogin+" has this permission already for budget '" + budgetId + "'.");
    }

}
