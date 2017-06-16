package com.example.java.application.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
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
import org.springframework.util.SystemPropertyUtils;

import com.example.java.application.services.ReportService;
import com.example.java.application.services.ReportServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.commons.exceptions.BudgetNotFoundException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.ExpenseReportItem;
import com.example.java.domain.model.IncomeReportItem;
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
		final List<ExpenseReportItem> reportFromRepository = stubRepositoryToGetReportWithExpenses();
		final List<ExpenseReportItem> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		


		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
		
		for(int i=0; i<reportFromRepository.size();i++){
			assertEquals("Returned amount and amount from repository should be the same",reportFromRepository.get(i).getAmount(),returnedReport.get(i).getAmount());
			assertEquals("Returned name and name from repository should be the same",reportFromRepository.get(i).getExpenseType().getName(),returnedReport.get(i).getExpenseType().getName());
			assertEquals("Returned image and image from repository should be the same",reportFromRepository.get(i).getExpenseType().getImage(),returnedReport.get(i).getExpenseType().getImage());
		}
	}

	@Test
	public void shouldGetReportWithIncomesForOneBugdetById() {
		final List<IncomeReportItem> reportFromRepository = stubRepositoryToGetReportWithIncomes();
		final List<IncomeReportItem> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
				
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);

		for(int i=0; i<reportFromRepository.size();i++){
			assertEquals("Returned amount and amount from repository should be the same",reportFromRepository.get(i).getAmount(),returnedReport.get(i).getAmount());
			assertEquals("Returned name and name from repository should be the same",reportFromRepository.get(i).getIncomeType().getName(),returnedReport.get(i).getIncomeType().getName());
			assertEquals("Returned image and image from repository should be the same",reportFromRepository.get(i).getIncomeType().getImage(),returnedReport.get(i).getIncomeType().getImage());
		}
	}

	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithExpensesForOneBugdetByIdForNotLoggedUser_TheExceptionShouldBeThrown() {
		final List<ExpenseReportItem> reportFromRepository = stubRepositoryToGetReportWithExpensesForNotLoggedUser();
		final List<ExpenseReportItem> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}

	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithExpensesForOneBugdetByIdForUserWithoutAccessView_TheExceptionShouldBeThrown() {
		final List<ExpenseReportItem> reportFromRepository = stubRepositoryToGetReportWithExpensesForUserWithoutAccessView();
		final List<ExpenseReportItem> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}

	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithIncomesForOneBugdetByIdForNotLoggedUser_TheExceptionShouldBeThrown() {
		final List<IncomeReportItem> reportFromRepository = stubRepositoryToGetReportWithIncomesForNotLoggedUser();
		final List<IncomeReportItem> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
				new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
		verify(userService, times(1)).getLoggedUserLogin();
		verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
	}

	@Test(expected = BudgetForbiddenAccessException.class)
	public void shouldGetReportWithIncomesForOneBugdetByIdForUserWithoutAccesView_TheExceptionShouldBeThrown() {
		final List<IncomeReportItem> reportFromRepository = stubRepositoryToGetReportWithIncomesForUserWithoutAccessView();
		final List<IncomeReportItem> returnedReport = reportService.getAllSumsIncomesPerType(BUDGET_ID,
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

	private BigDecimal stubRepositoryToGetDailyLimitForNotLoggedUser() {
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return dailyLimitFromRepository;
	}

	private BigDecimal stubRepositoryToGetDailyLimitForUserWithoutAccessView() {
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return dailyLimitFromRepository;
	}

	private BigDecimal stubRepositoryToGetDailyLimit() {
		final BigDecimal dailyLimitFromRepository = new BigDecimal(1.09).setScale(2, RoundingMode.DOWN);
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return dailyLimitFromRepository;
	}

	private List<ExpenseReportItem> stubRepositoryToGetReportWithExpensesForUserWithoutAccessView() {
		final List<ExpenseReportItem> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return reportFromRepository;
	}

	private List<IncomeReportItem> stubRepositoryToGetReportWithIncomesForUserWithoutAccessView() {
		final List<IncomeReportItem> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		return reportFromRepository;
	}

	private List<IncomeReportItem> stubRepositoryToGetReportWithIncomesForNotLoggedUser() {
		final List<IncomeReportItem> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

	private List<ExpenseReportItem> stubRepositoryToGetReportWithExpensesForNotLoggedUser() {
		final List<ExpenseReportItem> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

	private List<ExpenseReportItem> stubRepositoryToGetReportWithExpenses() {
		final List<ExpenseReportItem> reportFromRepository = createReportWithExpensesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

	private List<IncomeReportItem> stubRepositoryToGetReportWithIncomes() {
		final List<IncomeReportItem> reportFromRepository = createReportWithIncomesForBudget();
		final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN,
				PermissionType.VIEW);
		when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
		when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
		return reportFromRepository;
	}

}
