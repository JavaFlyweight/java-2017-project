package com.example.java.application.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.java.application.services.BudgetService;
import com.example.java.application.services.BudgetServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.application.services.UserServiceImpl;
import com.example.java.domain.model.User;
import com.example.java.domain.model.UserCreateRequest;
import com.example.java.repository.UserRepository;

public class UserServiceTest {

    private static final String IMAGE = "image";

    private static final String PASSWORD = "password";

    private static final String EMAIL = "email@email.pl";

    private static final String LAST_NAME = "lastName";

    private static final String NAME = "name";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private final UserService userService = new UserServiceImpl();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        User user = new User(NAME, LAST_NAME, EMAIL, PASSWORD, IMAGE);

        when(userRepository.save(Matchers.<User> any())).thenReturn(user);
        when(userRepository.findOneByEmail(Matchers.<String> any())).thenReturn(null);
    }


    @Test
    public void sholdSuccesfullRegisterUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail(EMAIL);
        userCreateRequest.setImage(IMAGE);
        userCreateRequest.setLastName(LAST_NAME);
        userCreateRequest.setName(NAME);
        userCreateRequest.setPassword(PASSWORD);

        User result = userService.registerUser(userCreateRequest);

        assertEquals(EMAIL, result.getEmail());
    }
}
