package com.example.java.application.services;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    public List<ExpenseType> getAllExpenseTypes();

    public Budget addNewExpense(UUID budgetId, Expense expense);

    public Budget deleteExpense(UUID budgetId, Expense expenseToDelete); 
}
