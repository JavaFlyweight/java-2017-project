package pl.lodz.p.java.flyweight.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BudgetNotFoundException extends RuntimeException {
    /**
     * Exception's serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetNotFoundException.class);

    /**
     * Exception's constructor
     */
    public BudgetNotFoundException(long id) {
        super("Budget with id "+id+ " not found.");
        LOGGER.error("Budget with id "+id+ " not found.");
    }
}