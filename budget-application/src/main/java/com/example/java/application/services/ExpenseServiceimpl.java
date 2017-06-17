package com.example.java.application.services;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.WrongDateValue;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.FinancialOperation;
import com.example.java.repository.BudgetRepository;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "expenseServiceImpl")
public class ExpenseServiceimpl implements FinancialOperationService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;
    
    @Autowired
    private UserService userService;

    @Override
    public ExpenseType [] getAllFinancialOperationTypes() { 
        return ExpenseType.values();
    }

    @Override
    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation expenseToAdd) {
        Budget budgetToAddExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        if (budgetToAddExpense.getDateFrom().after(expenseToAdd.getDateTime()) || budgetToAddExpense.getDateTo().before(expenseToAdd.getDateTime())){
           throw new WrongDateValue(budgetToAddExpense.getDateFrom(), budgetToAddExpense.getDateTo(), expenseToAdd.getDateTime());
        }
        Set<Expense> expenses = budgetToAddExpense.getExpenses();
        if (expenseToAdd.getAddedBy()!=null){
            expenseToAdd.setAddedByUserEmail(userService.getUser(expenseToAdd.getAddedBy()).getEmail());
        }
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
    public Budget addCopiedFinancialOperationWithNewDateToBudget(FinancialOperation expenseToCopy, Date newDateTime, UUID budgetId) {  
        Budget budgetToCopyExpense = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Expense> expenses = budgetToCopyExpense.getExpenses();
        Expense newExpense = new Expense((Expense) expenseToCopy);
        newExpense.setDateTime(newDateTime);
        expenses.add((Expense) expenseToCopy);
        budgetToCopyExpense.setExpenses(expenses);
 
       return budgetRepository.save(budgetToCopyExpense);
        
    }
}
