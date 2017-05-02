package com.example.java.application.services;

import java.util.List;
import java.util.UUID;

import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;

public interface BudgetService {

    public Budget getOneBudgetEntity(UUID budgetId);


    public Budget saveBudgetEntity(Budget budget);


    public List<Budget> getOneByUserIdAndOwner(UUID userID);


    public List<Budget> getSharedBudgets(UUID userID);


    public Budget addNewIncome(UUID budgetId, Income income);


    public Budget addNewExpense(UUID budgetId, Expense expense);
}
