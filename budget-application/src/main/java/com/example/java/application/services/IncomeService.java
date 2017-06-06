package com.example.java.application.services;

import com.example.java.commons.enums.IncomeType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
import java.util.List;
import java.util.UUID;

public interface IncomeService {

    public List<IncomeType> getAllIncomeTypes();

    public Budget addNewIncome(UUID budgetId, Income income);

    public Budget deleteIncome(UUID budgetId, Income incomeToDelete); 
}
