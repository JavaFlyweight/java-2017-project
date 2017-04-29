package com.example.java.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.java.domain.model.SecurityUserDetails;
import com.example.java.domain.model.User;
import com.example.java.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
		
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findOneByEmail(email);

		if (user == null){
			throw new UsernameNotFoundException("Cant find user " + email);
		}
		
		System.out.println(user.getUserRole());
		
		return new SecurityUserDetails(user);
	}
	
}
