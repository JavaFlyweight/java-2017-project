package com.example.java.application.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;

import static com.example.java.application.services.UtilsService.checkPermissionForBudget;

@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private UserService userService;

	@Override
	public Map<ExpenseType, Double> getAllSumsExpensesPerType(UUID budgetId, Date dateFrom, Date dateTo) {
		String userLogin = userService.getLoggedUserLogin();
		LOGGER.debug("Log user is  {}", new Object[] { userLogin });
		Map<ExpenseType, Double> allSumsExpensesPerType = new LinkedHashMap<ExpenseType, Double>();
		Budget budget = budgetRepository.findOneById(budgetId);
		if (!checkPermissionForBudget(budget, userLogin, PermissionType.OWNER, PermissionType.VIEW,
				PermissionType.EDIT)) {
			throw new BudgetForbiddenAccessException(budgetId);
		} else {
			Set<Expense> expenses = budget.getExpenses();
			List<ExpenseType> allExpenseTypes = new ArrayList<ExpenseType>(Arrays.asList(ExpenseType.values()));

			for (ExpenseType expenseType : allExpenseTypes) {
				Double sumExpensesOfType = expenses.stream().filter(type -> type.getExpenseType().equals(expenseType))
						.filter(nullDate -> nullDate.getDateTime() != null)
						.filter(date -> date.getDateTime().after(dateFrom) && date.getDateTime().before(dateTo))
						.mapToDouble(item -> item.getAmount()).sum();

				allSumsExpensesPerType.put(expenseType, sumExpensesOfType);
			}

			return allSumsExpensesPerType;
		}
	}

	@Override
	public Map<IncomeType, Double> getAllSumsIncomesPerType(UUID budgetId, Date dateFrom, Date dateTo) {
		String userLogin = userService.getLoggedUserLogin();
		LOGGER.debug("Log user is  {}", new Object[] { userLogin });
		Map<IncomeType, Double> allSumsIncomesPerType = new LinkedHashMap<IncomeType, Double>();
		Budget budget = budgetRepository.findOneById(budgetId);

		if (!checkPermissionForBudget(budget, userLogin, PermissionType.OWNER, PermissionType.VIEW,
				PermissionType.EDIT)) {
			throw new BudgetForbiddenAccessException(budgetId);
		} else {
			Set<Income> incomes = budget.getIncomes();
			List<IncomeType> allIncomeTypes = new ArrayList<IncomeType>(Arrays.asList(IncomeType.values()));

			for (IncomeType incomeType : allIncomeTypes) {
				Double sumIncomesOfType = incomes.stream().filter(type -> type.getIncomeType().equals(incomeType))
						.filter(nullDate -> nullDate.getDateTime() != null)
						.filter(date -> date.getDateTime().after(dateFrom) && date.getDateTime().before(dateTo))
						.mapToDouble(item -> item.getAmount()).sum();

				allSumsIncomesPerType.put(incomeType, sumIncomesOfType);
			}
			return allSumsIncomesPerType;
		}
	}

	@Override
	public BigDecimal getDailyLimit(UUID budgetId) {
		String userLogin = userService.getLoggedUserLogin();
		LOGGER.debug("Log user is  {}", new Object[] { userLogin });
		Budget budget = budgetRepository.findOneById(budgetId);
		BigDecimal dailyLimit;
		if (!checkPermissionForBudget(budget, userLogin, PermissionType.OWNER, PermissionType.VIEW,
				PermissionType.EDIT)) {
			throw new BudgetForbiddenAccessException(budgetId);
		} else {
			Date startDate = new Date();
			if (startDate.before(budget.getDateFrom())) {
				startDate = budget.getDateFrom();
			}
			if (budget.getDateTo().before(new Date())) {
				dailyLimit = new BigDecimal(0);
			} else {
				long numberOfDays = ((budget.getDateTo().getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24) + 1;
				dailyLimit = new BigDecimal(budget.getBalance() / numberOfDays);
			}
		}
		return dailyLimit.setScale(2, RoundingMode.DOWN);
	}

}
