package com.example.java.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.util.Pair;

import com.example.java.commons.enums.ExpenseType;


public interface ReportService {

	public List<Pair<ExpenseType, Double>> getAllSumsExpensesPerType(UUID budgetId);
	
}
