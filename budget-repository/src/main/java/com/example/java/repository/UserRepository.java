package com.example.java.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.java.domain.model.User;
import com.example.java.repository.customs.UserRepositoryCustom;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

}
