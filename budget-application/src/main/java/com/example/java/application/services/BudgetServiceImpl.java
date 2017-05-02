package com.example.java.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;


    @Override
    public Budget getOneBudgetEntity(UUID budgetId) {
        return budgetRepository.findOneById(budgetId);
    }


    @Override
    public Budget saveBudgetEntity(Budget budget) {
        return budgetRepository.save(budget);
    }


    @Override
    public List<Budget> getOneByUserIdAndOwner(UUID userID) {
        return budgetRepository.findOneByIdAndPermissions(userID, PermissionType.OWNER);
    }


    @Override
    public List<Budget> getSharedBudgets(UUID userID) {
        List<Budget> result = new ArrayList<>();
        result.addAll(budgetRepository.findOneByIdAndPermissions(userID, PermissionType.EDIT));
        result.addAll(budgetRepository.findOneByIdAndPermissions(userID, PermissionType.VIEW));

        return result;
    }


    @Override
    public Budget addNewIncome(UUID budgetId, Income income) {
        Budget editedBudget = getOneBudgetEntity(budgetId);

        if (editedBudget == null) {
            //throw new BudgetNotFoundException(budgetId);
        }

        Set<Income> incomes = editedBudget.getIncomes();
        incomes.add(income);

        editedBudget.setIncomes(incomes);

        budgetRepository.save(editedBudget);

        return editedBudget;
    }


    @Override
    public Budget addNewExpense(UUID budgetId, Expense expense) {
        Budget editedBudget = getOneBudgetEntity(budgetId);

        if (editedBudget == null) {
            //throw new BudgetNotFoundException(budgetId);
        }

        Set<Expense> expenses = editedBudget.getExpenses();
        expenses.add(expense);

        editedBudget.setExpenses(expenses);

        budgetRepository.save(editedBudget);

        return editedBudget;
    }

}
