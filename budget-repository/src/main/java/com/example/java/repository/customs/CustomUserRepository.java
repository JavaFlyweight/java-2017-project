package com.example.java.repository.customs;

import java.util.UUID;

import com.example.java.domain.model.User;

public interface CustomUserRepository {
	public User findOneByEmail(String email);

	public User findOneById(UUID userId);
}
