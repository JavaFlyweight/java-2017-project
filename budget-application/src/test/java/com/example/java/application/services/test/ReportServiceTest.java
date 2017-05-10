package com.example.java.application.services.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.application.services.ReportService;
import com.example.java.application.services.ReportServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.repository.BudgetRepository;

import static com.example.java.application.services.test.ReportTestUtils.createReportWithExpensesForBudget;

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
    public void shouldGetReportWithExpensesForOneBugdetById(){
    	final Map<ExpenseType, Double> reportFromRepository = stubRepositoryToGetReportWithExpenses();
    	final Map<ExpenseType, Double> returnedReport = reportService.getAllSumsExpensesPerType(BUDGET_ID, new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
    	verify(userService, times(1)).getLoggedUserLogin();
    	assertEquals("Returned report with expenses should be the same", reportFromRepository, returnedReport);
    }
    
    @Test
    public void shouldGetReportWithIncomesForOneBugdetById(){
    	
    }
    
    
    
    private Map<ExpenseType, Double> stubRepositoryToGetReportWithExpenses() {
        final Map<ExpenseType, Double> reportFromRepository=createReportWithExpensesForBudget();
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.VIEW);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        return reportFromRepository;
    }



    


}
