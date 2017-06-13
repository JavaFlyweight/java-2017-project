package com.example.java.webapp.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.UserService;
import com.example.java.domain.model.User;
import com.example.java.domain.model.UserCreateRequest;

@Controller
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String authorization) {
        String[] values = userService.getAuthorizationCredentials(authorization);

        String email = values[0];
        String password = values[1];

        return new ResponseEntity<User>(userService.getUser(email, password), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = userService.registerUser(userCreateRequest);

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody UserCreateRequest userCreateRequest, @RequestHeader("Authorization") String authorization) {
        String[] values = userService.getAuthorizationCredentials(authorization);

        String email = values[0];
        String password = values[1];

        User updatedUser = userService.updateUser(userCreateRequest, userService.getUser(email, password));

        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestHeader("Authorization") String authorization) {
        String[] values = userService.getAuthorizationCredentials(authorization);

        String email = values[0];
        String password = values[1];

        User deletedUser = userService.removeUser(userService.getUser(email, password));

        return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
    }
}
