package com.example.java.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.java.domain.model.Budget;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Override
	public Budget getOneBudgetEntity(UUID budgetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Budget saveBudgetEntity(Budget budget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Budget getOneByUserIdAndOwner(UUID userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Budget> getSharedBudgets(UUID userID) {
		// TODO Auto-generated method stub
		return null;
	}

}
