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
import com.example.java.domain.model.ExpenseReportItem;
import com.example.java.domain.model.IncomeReportItem;

public class ReportTestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestUtils.class);

	public static List<ExpenseReportItem> createReportWithExpensesForBudget() {
		final List<ExpenseReportItem> reportWithExpenses = new ArrayList<>();
		reportWithExpenses.add(new ExpenseReportItem(ExpenseType.HOUSEHOLD, 30.9));
		List<ExpenseType> allExpenseTypes = new ArrayList<ExpenseType>(Arrays.asList(ExpenseType.values()));
		for (ExpenseType et : allExpenseTypes) {
			if (!et.equals(ExpenseType.HOUSEHOLD)) {
				reportWithExpenses.add(new ExpenseReportItem(et, 0.0));
			}
		}
		return reportWithExpenses;
	}
	
	
	
	public static List<IncomeReportItem> createReportWithIncomesForBudget() {
		final List<IncomeReportItem> reportWithIncomes = new ArrayList<>();
		reportWithIncomes.add(new IncomeReportItem(IncomeType.SALARY, 0.00));
		reportWithIncomes.add(new IncomeReportItem(IncomeType.PREMIUM, 1000.99));
		List<IncomeType> allIncomeTypes = new ArrayList<IncomeType>(Arrays.asList(IncomeType.values()));
		for(IncomeType it: allIncomeTypes){
			if(!it.equals(IncomeType.PREMIUM) && !it.equals(IncomeType.SALARY)){
				reportWithIncomes.add(new IncomeReportItem(it, 0.0));
			}
		}
		return reportWithIncomes;
	}
	


}
