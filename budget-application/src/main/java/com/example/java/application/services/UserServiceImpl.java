package com.example.java.application.services;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.UUID;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.java.commons.exceptions.LoggedUserNotFoundException;
import com.example.java.commons.exceptions.UserAlreadyExist;
import com.example.java.commons.exceptions.UserNotFoundException;
import com.example.java.commons.exceptions.WrongCredentialsException;
import com.example.java.domain.model.User;
import com.example.java.domain.model.UserCreateRequest;
import com.example.java.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public void validateCredentials(User user, String password) {
        if (!user.getPassword().equals(password)) {
            throw new WrongCredentialsException();
        }
    }


    @Override
    public User getUser(UUID id) {
        User user = userRepository.findOneById(id);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }


    @Override
    public User getUser(String email, String password) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(email);
        }

        validateCredentials(user, password);

        return user;
    }


    @Override
    public User registerUser(UserCreateRequest userCreateRequest) {
        String name = userCreateRequest.getName();
        String lastName = userCreateRequest.getLastName();
        String email = userCreateRequest.getEmail();
        String password = userCreateRequest.getPassword();
        String image = userCreateRequest.getImage();

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new BadRequestException();
        }

        if (isUserExist(email)) {
            throw new UserAlreadyExist(email);
        }

        return userRepository.save(new User(name, lastName, email, password, image));
    }


    @Override
    public boolean isUserExist(String email) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            return false;
        }

        return true;
    }


    @Override
    public String getLoggedUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userLogin = null;
        if (principal instanceof com.example.java.domain.model.SecurityUserDetails) {
            com.example.java.domain.model.SecurityUserDetails loggedUser = (com.example.java.domain.model.SecurityUserDetails) principal;
            userLogin = loggedUser.getUsername();
        }
        LOGGER.info("Logged user is  {}", new Object[] { userLogin });
        if (userLogin == null) {
            throw new LoggedUserNotFoundException();
        }
        return userLogin;
    }


    @Override
    public User updateUser(UserCreateRequest userCreateRequest, User actualUser) {
        String name = userCreateRequest.getName();
        String lastName = userCreateRequest.getLastName();
        String email = userCreateRequest.getEmail();
        String password = userCreateRequest.getPassword();
        String image = userCreateRequest.getImage();

        if (name != null && !name.isEmpty()) {
            actualUser.setName(name);
        }

        if (lastName != null && !lastName.isEmpty()) {
            actualUser.setLastName(lastName);
        }

        if (email != null && !email.isEmpty()) {
            actualUser.setEmail(email);
        }

        if (password != null && !password.isEmpty()) {
            actualUser.setPassword(password);
        }

        if (image != null && !image.isEmpty()) {
            actualUser.setImage(image);
        }

        return userRepository.save(actualUser);
    }


    @Override
    public String[] getAuthorizationCredentials(String authorization) {
        if (authorization == null || !authorization.startsWith("Basic")) {
            //TODO dsad
        }

        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));

        return credentials.split(":", 2);
    }


    @Override
    public User removeUser(User user) {
        userRepository.delete(user);

        return user;
    }
}
