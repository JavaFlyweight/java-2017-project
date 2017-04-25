package pl.lodz.p.java.flyweight.exception;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserAlredyExistException extends BadRequestException {

    /**
     * Exception's serialVersionUID
     */
    private static final String EXCEPTION_MESSAGE = "User {email} alredy exist";

    private static final String EMAIL_PLACEHOLDER = "{email}";

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAlredyExistException.class);


    /**
     * Exception's constructor
     */
    public UserAlredyExistException(String email) {
        super(EXCEPTION_MESSAGE.replace(EMAIL_PLACEHOLDER, email));

        LOGGER.error(EXCEPTION_MESSAGE.replace(EMAIL_PLACEHOLDER, email));
    }
}