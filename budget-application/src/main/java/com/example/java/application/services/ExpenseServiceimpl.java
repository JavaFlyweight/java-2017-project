package com.example.java.application.services;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.FinancialOperation;
import com.example.java.repository.BudgetRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "expenseServiceImpl")
public class ExpenseServiceimpl implements FinancialOperationService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Override
    public Map<ExpenseType,String> getAllFinancialOperationTypes() {
    Map<ExpenseType, String> expenseTypesMap = new HashMap<>();
        for (ExpenseType expenseType : ExpenseType.values()) {
            expenseTypesMap.put(expenseType, expenseType.getImage());
        }
        return expenseTypesMap;
    }

    @Override
    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation expenseToAdd) {
        Budget budgetToAddExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Expense> expenses = budgetToAddExpense.getExpenses();
        expenses.add((Expense) expenseToAdd);
        budgetToAddExpense.setExpenses(expenses);
        budgetToAddExpense.setBalance(budgetToAddExpense.getBalance() - expenseToAdd.getAmount());
        return budgetRepository.save(budgetToAddExpense);
    }

    @Override
    public Budget deleteFinancialOperation(UUID budgetId, FinancialOperation expenseToDelete) {
        Budget budgetToDeleteExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Expense> expenses = budgetToDeleteExpense.getExpenses();
        Expense expenseToCompare = (Expense) expenseToDelete;
        for (Expense expense : expenses) {
            if (expense.getAddedBy().equals(expenseToCompare.getAddedBy()) && expense.getAmount() == expenseToCompare.getAmount() && expense.getDateTime().equals(expenseToCompare.getDateTime()) && expense.getExpenseType().equals(expenseToCompare.getExpenseType()) && expense.getName().equals(expenseToCompare.getName())) {
                expenses.remove(expense);
                break;
            }
        }
        budgetToDeleteExpense.setExpenses(expenses);
        budgetToDeleteExpense.setBalance(budgetToDeleteExpense.getBalance() + expenseToDelete.getAmount());
        return budgetRepository.save(budgetToDeleteExpense);
    }
    
    @Override
    public Expense copyWithNewDate(FinancialOperation incomeToCopy, Date newDateTime) {
        Expense newIncome = new Expense((Expense) incomeToCopy);
        newIncome.setDateTime(newDateTime);
        return newIncome;
    }
}
