package com.example.java.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.java.domain.model.Budget;
import com.example.java.repository.customs.BudgetRepositoryCustom;

@Repository
public interface BudgetRepository extends MongoRepository<Budget, String>, BudgetRepositoryCustom {

}
