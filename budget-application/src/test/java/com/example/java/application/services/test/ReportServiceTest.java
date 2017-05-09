package com.example.java.application.services.test;

import static org.mockito.Mockito.when;

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
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.repository.BudgetRepository;

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
    	
    }
    
    @Test
    public void shouldGetReportWithIncomesForOneBugdetById(){
    	
    }
    
    
    
    private Budget stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType permissionType) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, permissionType);
//        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
//        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        return budgetFromRepository;
    }

    
    


}
