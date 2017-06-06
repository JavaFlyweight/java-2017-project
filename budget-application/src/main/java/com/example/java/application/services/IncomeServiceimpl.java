package com.example.java.application.services;

import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceimpl implements IncomeService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Override
    public List<IncomeType> getAllIncomeTypes() {
        return new ArrayList<>(Arrays.asList(IncomeType.values()));
    }

    @Override
    public Budget addNewIncome(UUID budgetId, Income incomeToAdd) {
        Budget budgetToAddIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Income> incomes = budgetToAddIncome.getIncomes();
        incomes.add(incomeToAdd);
        budgetToAddIncome.setIncomes(incomes);
        budgetToAddIncome.setBalance(budgetToAddIncome.getBalance()+incomeToAdd.getAmount());
        return budgetRepository.save(budgetToAddIncome);
    }

    @Override
    public Budget deleteIncome(UUID budgetId, Income incomeToDelete) {
        Budget budgetToAddIncome = budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Set<Income> incomes = budgetToAddIncome.getIncomes();
        for (Income income : incomes) {
            if (income.getAddedBy().equals(incomeToDelete.getAddedBy()) && income.getAmount() == incomeToDelete.getAmount() && income.getDateTime().equals(incomeToDelete.getDateTime()) && income.getIncomeType().equals(incomeToDelete.getIncomeType()) && income.getName().equals(incomeToDelete.getName())) {
                incomes.remove(income);
                break;
            }
        }
        budgetToAddIncome.setIncomes(incomes);
        budgetToAddIncome.setBalance(budgetToAddIncome.getBalance()-incomeToDelete.getAmount());
        return budgetRepository.save(budgetToAddIncome);
    }
}
