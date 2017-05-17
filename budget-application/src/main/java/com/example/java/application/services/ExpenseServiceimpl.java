package com.example.java.application.services;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.repository.BudgetRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceimpl implements ExpenseService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Override
    public List<ExpenseType> getAllExpenseTypes() {
        return new ArrayList<>(Arrays.asList(ExpenseType.values()));
    }

    @Override
    public Budget addNewExpense(UUID budgetId, Expense expenseToAdd) {
        Budget budgetToAddExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Expense> expenses = budgetToAddExpense.getExpenses();
        expenses.add(expenseToAdd);
        budgetToAddExpense.setExpenses(expenses);
        return budgetRepository.save(budgetToAddExpense);
    }

    @Override
    public Budget deleteExpense(UUID budgetId, Expense expenseToDelete) {
        Budget budgetToAddExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Expense> expenses = budgetToAddExpense.getExpenses();
        
        for (Expense expense : expenses) {
            if (expense.getAddedBy().equals(expenseToDelete.getAddedBy()) && expense.getAmount() == expenseToDelete.getAmount() && expense.getDateTime().equals(expenseToDelete.getDateTime()) && expense.getExpenseType().equals(expenseToDelete.getExpenseType()) && expense.getName().equals(expenseToDelete.getName())) {
                expenses.remove(expense);
                break;
            }
        }
        budgetToAddExpense.setExpenses(expenses);
        return budgetRepository.save(budgetToAddExpense);
    }
}
