package com.example.java.application.services.test;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.domain.model.Budget;

public class ReportTestUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestUtils.class);
	
	public static Map<ExpenseType, Double> createReportWithExpensesForBudget(){
		final Map<ExpenseType, Double> reportWithExpenses = new HashMap<>();
		reportWithExpenses.put(ExpenseType.HOUSEHOLD, 30.9);		
		
		return reportWithExpenses;
	}

}
