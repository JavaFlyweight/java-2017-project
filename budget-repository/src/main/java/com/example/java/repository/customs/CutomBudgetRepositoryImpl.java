package com.example.java.repository.customs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.repository.configuration.MongoConfiguration;
import com.example.java.repository.queries.BudgetQuery;

public class CutomBudgetRepositoryImpl implements MongoConfiguration, CustomBudgetRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Budget findOneById(UUID budgetId) {
		return mongoTemplate.findById(budgetId, Budget.class);
	}

	@Override
	public List<Budget> findAllByIdAndPermissions(UUID userId, PermissionType permission) {
		return mongoTemplate.find(BudgetQuery.queryFindAllByUserIdAndPermissions(userId, permission), Budget.class);
	}
}
