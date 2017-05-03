package com.example.java.repository.customs;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.repository.queries.BudgetQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BudgetRepositoryImpl implements  BudgetRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Budget findOneById(UUID budgetId) {
		return mongoTemplate.findById(budgetId, Budget.class);
	}

	@Override
	public List<Budget> findAllByUserLoginAndPermission(String userLogin, PermissionType permission) {
		return mongoTemplate.find(BudgetQuery.queryFindAllByUserLoginAndPermissions(userLogin, permission), Budget.class);
	}
}
