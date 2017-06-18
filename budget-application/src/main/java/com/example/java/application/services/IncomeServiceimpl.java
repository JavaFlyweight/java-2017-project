package com.example.java.application.services;

import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.WrongDateValue;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.FinancialOperation;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "incomeServiceImpl")
public class IncomeServiceimpl implements FinancialOperationService {
    
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;
    
    @Autowired
    private UserService userService;

    @Override
    public IncomeType[] getAllFinancialOperationTypes() {
        return IncomeType.values();
    }

    @Override
    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation incomeToAdd) {
        Budget budgetToAddIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
  
        if (budgetToAddIncome.getDateFrom().after(incomeToAdd.getDateTime()) || budgetToAddIncome.getDateTo().before(incomeToAdd.getDateTime())){
           throw new WrongDateValue(budgetToAddIncome.getDateFrom(), budgetToAddIncome.getDateTo(), incomeToAdd.getDateTime());
        }
        Set<Income> incomes = budgetToAddIncome.getIncomes();
        if (incomeToAdd.getAddedBy()!=null){
            incomeToAdd.setAddedByUserEmail(userService.getUser(incomeToAdd.getAddedBy()).getEmail());
        }
        incomes.add((Income) incomeToAdd);
        budgetToAddIncome.setIncomes(incomes);
        budgetToAddIncome.setBalance(budgetToAddIncome.getBalance() + incomeToAdd.getAmount());
        return budgetRepository.save(budgetToAddIncome);
    }

    @Override
    public Budget deleteFinancialOperation(UUID budgetId, FinancialOperation incomeToDelete) {
        Budget budgetToDeleteIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Income incomeToCompare = (Income) incomeToDelete;
        Set<Income> incomes = budgetToDeleteIncome.getIncomes();
        for (Income income : incomes) {
            if (income.getAddedBy().equals(incomeToCompare.getAddedBy()) && income.getAmount() == incomeToCompare.getAmount() && income.getDateTime().equals(incomeToCompare.getDateTime()) && income.getIncomeType().equals(incomeToCompare.getIncomeType()) && income.getName().equals(incomeToCompare.getName())) {
                incomes.remove(income);
                break;
            }
        }
        budgetToDeleteIncome.setIncomes(incomes);
        budgetToDeleteIncome.setBalance(budgetToDeleteIncome.getBalance() - incomeToCompare.getAmount());
        return budgetRepository.save(budgetToDeleteIncome);
    }

    @Override
    public Budget addCopiedFinancialOperationWithNewDateToBudget(FinancialOperation incomeToCopy, Date newDateTime, UUID budgetId) {
        Budget budgetToCopyIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Income> incomes = budgetToCopyIncome.getIncomes();
        Income newIncome = new Income((Income) incomeToCopy);
        newIncome.setDateTime(newDateTime);
        incomes.add((Income) newIncome);
        budgetToCopyIncome.setIncomes(incomes);
 
       return budgetRepository.save(budgetToCopyIncome);
    }
}
