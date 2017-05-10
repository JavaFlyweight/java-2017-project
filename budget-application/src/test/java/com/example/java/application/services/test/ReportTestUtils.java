package com.example.java.application.services.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.domain.model.Budget;

public class ReportTestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestUtils.class);

	public static Map<ExpenseType, Double> createReportWithExpensesForBudget() {
		final Map<ExpenseType, Double> reportWithExpenses = new LinkedHashMap<>();
		reportWithExpenses.put(ExpenseType.HOUSEHOLD, 30.9);
		List<ExpenseType> allExpenseTypes = new ArrayList<ExpenseType>(Arrays.asList(ExpenseType.values()));
		for (ExpenseType et : allExpenseTypes) {
			if (!et.equals(ExpenseType.HOUSEHOLD)) {
				reportWithExpenses.put(et, 0.0);
			}
		}
		return reportWithExpenses;
	}
	
	
	
	public static Map<IncomeType, Double> createReportWithIncomesForBudget() {
		final Map<IncomeType, Double> reportWithIncomes = new HashMap<>();
		reportWithIncomes.put(IncomeType.PREMIUM, 1000.99);
		List<IncomeType> allIncomeTypes = new ArrayList<IncomeType>(Arrays.asList(IncomeType.values()));
		for(IncomeType it: allIncomeTypes){
			if(!it.equals(IncomeType.PREMIUM)){
				reportWithIncomes.put(it, 0.0);
			}
		}
		return reportWithIncomes;
	}

}
