package com.example.java.application.services.test;

import com.example.java.application.services.BudgetService;
import com.example.java.application.services.FinancialOperationService;
import com.example.java.application.services.IncomeServiceimpl;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
import com.example.java.repository.BudgetRepository;
import java.util.Date;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class IncomeServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private final FinancialOperationService incomeService = new IncomeServiceimpl();

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final String USER_LOGIN = "abc@wp.pl";

    @Test
    public void shouldAddNewIncome_BugdetsSholdBeTheSame() {
        final Income incomeToAdd = new Income("Premia", 1200.0, UUID.randomUUID(), IncomeType.PREMIUM, new Date());
        final Budget budgetFromRepository = stubRepositoryToAddNewIncome(incomeToAdd);
        final Budget returnedBudget = incomeService.addNewFinancialOperation(BUDGET_ID, incomeToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned budget should be the same as mock budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldAddNewIncome_IncomeSetShouldCorectSize() {
        final Income incomeToAdd = new Income("Premia", 1200.0, UUID.randomUUID(), IncomeType.PREMIUM, new Date());
        stubRepositoryToAddNewIncome(incomeToAdd);
        final Budget returnedBudget = incomeService.addNewFinancialOperation(BUDGET_ID, incomeToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned income size should has correct size", returnedBudget.getIncomes().size(), 3);
    }

    @Test
    public void shouldDeleteIncome_BugdetsSholdBeTheSame() {
        final Income incomeToAdd = new Income("Premia", 1200.0, UUID.randomUUID(), IncomeType.PREMIUM, new Date());
        final Budget budgetFromRepository = stubRepositoryToDeleteIncome(incomeToAdd);
        final Budget returnedBudget = incomeService.deleteFinancialOperation(BUDGET_ID, incomeToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned budget should be the same as mock budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldDeleteIncome_IncomeSetShouldCorectSize() {
       final Income incomeToAdd = new Income("Premia", 1200.0, UUID.randomUUID(), IncomeType.PREMIUM, new Date());
        stubRepositoryToDeleteIncome(incomeToAdd);
        final Budget returnedBudget = incomeService.deleteFinancialOperation(BUDGET_ID, incomeToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned income size should has correct size", returnedBudget.getIncomes().size(), 2);
    }

    private Budget stubRepositoryToAddNewIncome(Income incomeToAdd) {
        final Budget budgetFromRepository = BudgetTestUtils.createBudgetEntityForTestExpensesAndIncome(USER_LOGIN);
        final Budget budgetToSave = BudgetTestUtils.createBudgeWithAdditionalIncome(budgetFromRepository, incomeToAdd);
        when(budgetService.getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT)).thenReturn(budgetFromRepository);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);
        return budgetToSave;
    }

    private Budget stubRepositoryToDeleteIncome(Income incomeToAdd) {
        final Budget budgetToSave = BudgetTestUtils.createBudgetEntityForTestExpensesAndIncome(USER_LOGIN);
        final Budget budgetFromRepository = BudgetTestUtils.createBudgeWithAdditionalIncome(budgetToSave, incomeToAdd);

        when(budgetService.getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT)).thenReturn(budgetFromRepository);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);
        return budgetToSave;
    }

}
