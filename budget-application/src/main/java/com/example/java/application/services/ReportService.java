package com.example.java.application.services;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;

public interface ReportService {

	public Map<ExpenseType, Double> getAllSumsExpensesPerType(UUID budgetId, Date dateFrom, Date dateTo);
	
	public Map<IncomeType, Double> getAllSumsIncomesPerType(UUID budgetId, Date dateFrom, Date dateTo);

}