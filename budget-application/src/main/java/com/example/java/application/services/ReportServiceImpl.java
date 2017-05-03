package com.example.java.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.repository.BudgetRepository;

@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

	@Override
	public List<Pair<ExpenseType, Double>> getAllSumsExpensesPerType(UUID budgetId) {
		List<Pair<ExpenseType,Double>> allSumsExpensesPerType = new ArrayList<>();
		
		
		
		return allSumsExpensesPerType;
	}
    


}
