package pl.lodz.p.java.flyweight.service;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import pl.lodz.p.java.flyweight.exception.UserAlredyExistException;
import pl.lodz.p.java.flyweight.exception.UserNotFoundException;
import pl.lodz.p.java.flyweight.model.User;
import pl.lodz.p.java.flyweight.repository.UserRepository;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private final UserRepository userRepository;


    @Inject
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private boolean checkUserExist(String email, String password) {
        User user = userRepository.findOneByEmailAndPassword(email, password);
        if (user != null) {
            return true;
        }
        return false;
    }


    @Override
    public void registerUser(Map<String, String> requestParams) {
        String userEmail = requestParams.get(EMAIL);
        String userPassword = requestParams.get(PASSWORD);

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            throw new BadRequestException();
        }

        if (checkUserExist(userEmail, userPassword)) {
            throw new UserAlredyExistException(userEmail);
        }

        userRepository.save(new User(userEmail, userPassword));
    }


    @Override
    public UUID getUserId(Map<String, String> requestParams) {
        String userEmail = requestParams.get(EMAIL);
        String userPassword = requestParams.get(PASSWORD);

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            throw new BadRequestException();
        }

        User user = userRepository.findOneByEmailAndPassword(userEmail, userPassword);

        if (user == null) {
            throw new UserNotFoundException(userEmail);
        }

        return user.getId();
    }

}
