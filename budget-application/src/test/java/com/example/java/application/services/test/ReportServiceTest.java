package com.example.java.application.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import com.example.java.application.services.ReportService;
import com.example.java.application.services.ReportServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.commons.exceptions.BudgetNotFoundException;
import com.example.java.domain.model.Budget;
import com.example.java.repository.BudgetRepository;

import static com.example.java.application.services.test.ReportTestUtils.createReportWithExpensesForBudget;
import static com.example.java.application.services.test.ReportTestUtils.createReportWithIncomesForBudget;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

	@Mock
	private BudgetRepository budgetRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private final ReportService reportService = new ReportServiceImpl();

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceTest.class);

	private static final UUID BUDGET_ID = UUID.randomUUID();
	private static final String USER_LOGIN = "abc@wp.pl";
	private static final String USER_LOGIN2 = "xyz@wp.pl";

	@Test
	public void shouldGetReportWithExpensesForOneBugdetById() {
		final Map<ExpenseType, Double> reportFromRepository = stubRepositoryToGetReportWithExpenses();
		final Map<ExpenseType, Double> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));

		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
		assertEquals("Returned report with expenses should be the same", reportFromRepository, returnedReport);
	}

	@Test
	public void shouldGetReportWithIncomesForOneBugdetById() {
		final Map<IncomeType, Double> reportFromRepository = stubRepositoryToGetReportWithIncomes();
		final Map<IncomeType, Double> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
		assertEquals("Returned report with incomes should be the same", reportFromRepository, returnedReport);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithExpensesForOneBugdetByIdForNotLoggedUser_TheExceptionShouldBeThrown(){
		final Map<ExpenseType, Double> reportFromRepository = stubRepositoryToGetReportWithExpensesForNotLoggedUser();
		final Map<ExpenseType, Double> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithExpensesForOneBugdetByIdForUserWithoutAccessView_TheExceptionShouldBeThrown(){
		final Map<ExpenseType, Double> reportFromRepository = stubRepositoryToGetReportWithExpensesForUserWithoutAccessView();
		final Map<ExpenseType, Double> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithIncomesForOneBugdetByIdForNotLoggedUser_TheExceptionShouldBeThrown(){
		final Map<IncomeType, Double> reportFromRepository = stubRepositoryToGetReportWithIncomesForNotLoggedUser();
		final Map<IncomeType, Double> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithIncomesForOneBugdetByIdForUserWithoutAccesView_TheExceptionShouldBeThrown(){
		final Map<IncomeType, Double> reportFromRepository = stubRepositoryToGetReportWithIncomesForUserWithoutAccessView();
		final Map<IncomeType, Double> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	

	@Test
	public void shouldGetDailyLimitForBugdetById() {
		final BigDecimal limitFromRepository = stubRepositoryToGetDailyLimit();
		final BigDecimal returnedLimit = reportService.getDailyLimit(BUDGET_ID);

		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
		assertEquals("Returned limits should be the same", limitFromRepository, returnedLimit);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetDailyLimitForBugdetByIdForUserWithoutAccesView_TheExceptionShouldBeThrown() {
		final BigDecimal limitFromRepository = stubRepositoryToGetDailyLimitForUserWithoutAccessView();
		final BigDecimal returnedLimit = reportService.getDailyLimit(BUDGET_ID);

		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	
	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetDailyLimitForBugdetByIdForNotLoggedUser_TheExceptionShouldBeThrown() {
		final BigDecimal limitFromRepository = stubRepositoryToGetDailyLimitForNotLoggedUser();
		final BigDecimal returnedLimit = reportService.getDailyLimit(BUDGET_ID);

		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}
	
	private BigDecimal stubRepositoryToGetDailyLimitForNotLoggedUser(){
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return dailyLimitFromRepository;
	}
	
	private BigDecimal stubRepositoryToGetDailyLimitForUserWithoutAccessView(){
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return dailyLimitFromRepository;
	}
	
	private BigDecimal stubRepositoryToGetDailyLimit(){
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return dailyLimitFromRepository;
	}
	
	
	
	private Map<ExpenseType,Double> stubRepositoryToGetReportWithExpensesForUserWithoutAccessView(){
		final Map<ExpenseType, Double> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return reportFromRepository;
	}
	
	private Map<IncomeType,Double> stubRepositoryToGetReportWithIncomesForUserWithoutAccessView(){
		final Map<IncomeType, Double> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return reportFromRepository;
	}
	
	private Map<IncomeType,Double> stubRepositoryToGetReportWithIncomesForNotLoggedUser(){
		final Map<IncomeType, Double> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}
	
	private Map<ExpenseType,Double> stubRepositoryToGetReportWithExpensesForNotLoggedUser(){
		final Map<ExpenseType, Double> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}
	
	private Map<ExpenseType, Double> stubRepositoryToGetReportWithExpenses() {
		final Map<ExpenseType, Double> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

	private Map<IncomeType, Double> stubRepositoryToGetReportWithIncomes() {
		final Map<IncomeType, Double> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

}
