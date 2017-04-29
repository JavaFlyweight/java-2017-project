package com.example.java.repository.configuration;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoConfiguration {
	public void setMongoTemplate(MongoTemplate mongoTemplate);
}
