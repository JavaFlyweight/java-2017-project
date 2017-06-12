package com.example.java.application.services;

import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.FinancialOperation;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public Map<IncomeType, String> getAllFinancialOperationTypes() {
        Map<IncomeType, String> incomeTypesMap = new HashMap<>();
        for (IncomeType incomeType : IncomeType.values()) {
            incomeTypesMap.put(incomeType, incomeType.getImage());
        }
        return incomeTypesMap;
    }

    @Override
    public Budget addNewFinancialOperation(UUID budgetId, FinancialOperation incomeToAdd) {
        Budget budgetToAddIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Income> incomes = budgetToAddIncome.getIncomes();
        incomes.add((Income) incomeToAdd);
        budgetToAddIncome.setIncomes(incomes);
        budgetToAddIncome.setBalance(budgetToAddIncome.getBalance() + incomeToAdd.getAmount());
        return budgetRepository.save(budgetToAddIncome);
    }

    @Override
    public Budget deleteFinancialOperation(UUID budgetId, FinancialOperation incomeToDelete) {
        Budget budgetToAddIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Income incomeToCompare = (Income) incomeToDelete;
        Set<Income> incomes = budgetToAddIncome.getIncomes();
        for (Income income : incomes) {
            if (income.getAddedBy().equals(incomeToCompare.getAddedBy()) && income.getAmount() == incomeToCompare.getAmount() && income.getDateTime().equals(incomeToCompare.getDateTime()) && income.getIncomeType().equals(incomeToCompare.getIncomeType()) && income.getName().equals(incomeToCompare.getName())) {
                incomes.remove(income);
                break;
            }
        }
        budgetToAddIncome.setIncomes(incomes);
        budgetToAddIncome.setBalance(budgetToAddIncome.getBalance() - incomeToCompare.getAmount());
        return budgetRepository.save(budgetToAddIncome);
    }

    @Override
    public Income copyWithNewDate(FinancialOperation incomeToCopy, Date newDateTime) {
        Income newIncome = new Income((Income) incomeToCopy);
        newIncome.setDateTime(newDateTime);
        return newIncome;
    }
}
