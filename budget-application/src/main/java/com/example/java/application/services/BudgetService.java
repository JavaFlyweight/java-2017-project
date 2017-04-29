package com.example.java.application.services;

import java.util.List;
import java.util.UUID;

import com.example.java.domain.model.Budget;

public interface BudgetService {

	public Budget getOneBudgetEntity(UUID budgetId);

	public Budget saveBudgetEntity(Budget budget);

	public Budget getOneByUserIdAndOwner(UUID userID);

	public List<Budget> getSharedBudgets(UUID userID);
}
