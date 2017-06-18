package com.example.java.commons.exceptions;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongDateValue extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(WrongDateValue.class);


    public WrongDateValue(Date dateFrom, Date dateTo) {
        super("Wrong date value - dateFrom " + dateFrom + " is after dateTo "+ dateTo+".");
        LOGGER.error("Wrong date value - dateFrom " + dateFrom + " is after dateTo "+ dateTo+".");
    }
    
    public WrongDateValue(Date dateTo) {
        super("Wrong date value - dateTo" + dateTo + " is before today.");
        LOGGER.error("Wrong date value for dateFrom " + dateTo + " and dateTo "+ dateTo+".");
    }
    
    public WrongDateValue(Date dateFrom, Date dateTo, Date financialOperationDate) {
        super("Wrong date value - date "+financialOperationDate+" isn't between dateFrom " + dateFrom + " and dateTo "+ dateTo+".");
        LOGGER.error("Wrong date value - date "+financialOperationDate+" isn't between dateFrom " + dateFrom + " and dateTo "+ dateTo+".");
    }
}
