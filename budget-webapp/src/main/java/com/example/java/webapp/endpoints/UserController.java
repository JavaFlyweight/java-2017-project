package com.example.java.webapp.endpoints;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.UserService;

@Controller
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static final String PASSWORD = "password";
	private static final String EMAIL = "email";
	private static final String LAST_NAME = "lastName";
	private static final String NAME = "name";
        private static final String IMAGE = "image";
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Map<String, UUID>> registerNewUser(@RequestBody Map<String, String> requestParams) {
		String name = requestParams.get(NAME);
		String lastName = requestParams.get(LAST_NAME);
		String email = requestParams.get(EMAIL);
		String password = requestParams.get(PASSWORD);
		String image = requestParams.get(IMAGE);
                
		UUID newUserId = userService.registerUser(name, lastName, email, password, image);
		
		HashMap<String, UUID> response = new HashMap<>();
		response.put("id", newUserId);
		
		return new ResponseEntity<Map<String, UUID>>(response, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/greetings", method = RequestMethod.GET)
    public Map<String, String> greetings() {
    	Map<String, String> result = new HashMap<>();
    	result.put("id", UUID.randomUUID().toString());
    	result.put("name", "Random Name");
    	
    	return result;
    }
}
