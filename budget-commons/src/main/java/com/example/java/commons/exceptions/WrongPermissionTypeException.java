package com.example.java.commons.exceptions;

import com.example.java.commons.enums.PermissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongPermissionTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(WrongPermissionTypeException.class);

    public WrongPermissionTypeException(PermissionType permissionType) {
        super("Permission type is wrong '" + permissionType + "'.");
        LOGGER.error("Permission type is wrong '" + permissionType + "'.");
    }


}
