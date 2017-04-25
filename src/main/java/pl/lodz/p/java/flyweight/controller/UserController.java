package pl.lodz.p.java.flyweight.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.lodz.p.java.flyweight.model.User;
import pl.lodz.p.java.flyweight.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;


    @Inject
    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> userRegistration(@RequestBody Map<String, String> requestParams) {
        userService.registerUser(requestParams);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
