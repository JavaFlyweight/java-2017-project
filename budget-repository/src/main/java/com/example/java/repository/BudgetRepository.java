package com.example.java.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.java.domain.model.Budget;
import com.example.java.repository.customs.BudgetRepositoryCustom;

public interface BudgetRepository extends MongoRepository<Budget, String>, BudgetRepositoryCustom {
	
}
