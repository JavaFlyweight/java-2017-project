package com.example.java.application.services;

import com.example.java.commons.enums.PermissionType;
import java.util.List;
import java.util.UUID;

import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;

public interface BudgetService {

    public Budget getOneById(UUID budgetId, PermissionType... permissionTypes);

    public Budget createBudgetEntity(Budget dataToCreateBudget);
    
    public Budget getOneByUserLoginAndOwner();


    public List<Budget> getSharedBudgets();

    public Budget editBudgetEntity(Budget dataToEditBudget);

    public Budget addNewIncome(UUID budgetId, Income income);


    public Budget addNewExpense(UUID budgetId, Expense expense);
}
