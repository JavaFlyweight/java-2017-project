package com.example.java.repository.customs;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.java.domain.model.User;
import com.example.java.repository.configuration.MongoConfiguration;
import com.example.java.repository.queries.UserQuery;

public class CustomUserRepositoryImpl implements MongoConfiguration, CustomUserRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
	public User findOneByEmail(String email) {
		return mongoTemplate.findOne(UserQuery.queryFindOneByEmail(email), User.class);
	}

	@Override
	public User findOneById(UUID userId) {
		return mongoTemplate.findById(userId, User.class);
	}
}
