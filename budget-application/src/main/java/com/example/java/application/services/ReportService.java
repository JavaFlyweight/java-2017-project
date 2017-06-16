package com.example.java.application.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.domain.model.ExpenseReportItem;
import com.example.java.domain.model.IncomeReportItem;

public interface ReportService {

	public List<ExpenseReportItem> getAllSumsExpensesPerType(UUID budgetId, Date dateFrom, Date dateTo);
	
	public List<IncomeReportItem> getAllSumsIncomesPerType(UUID budgetId, Date dateFrom, Date dateTo);
	
	public BigDecimal getDailyLimit(UUID budgetId);

}
