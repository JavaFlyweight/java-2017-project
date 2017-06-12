package com.example.java.application.services.test;

import com.example.java.application.services.BudgetService;
import com.example.java.application.services.ExpenseServiceimpl;
import com.example.java.application.services.FinancialOperationService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
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
public class ExpenseServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private final FinancialOperationService expenseService = new ExpenseServiceimpl();

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final String USER_LOGIN = "abc@wp.pl";

    @Test
    public void shouldAddNewExpense_BugdetsSholdBeTheSame() {
        final Expense expenseToAdd = new Expense("Lekarz", 150.0, null, ExpenseType.HEALTHCARE, new Date());
        final Budget budgetFromRepository = stubRepositoryToAddNewExpense(expenseToAdd);
        final Budget returnedBudget = expenseService.addNewFinancialOperation(BUDGET_ID, expenseToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned budget should be the same as mock budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldAddNewExpense_ExpenseSetShouldCorectSize() {
        final Expense expenseToAdd = new Expense("Lekarz", 150.0, null, ExpenseType.HEALTHCARE, new Date());
        stubRepositoryToAddNewExpense(expenseToAdd);
        final Budget returnedBudget = expenseService.addNewFinancialOperation(BUDGET_ID, expenseToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned expense size should has correct size", returnedBudget.getExpenses().size(), 5);
    }

    @Test
    public void shouldDeleteExpense_BugdetsSholdBeTheSame() {
        final Expense expenseToAdd = new Expense("Lekarz", 150.0, UUID.randomUUID(), ExpenseType.HEALTHCARE, new Date());
        final Budget budgetFromRepository = stubRepositoryToDeleteExpense(expenseToAdd);
        final Budget returnedBudget = expenseService.deleteFinancialOperation(BUDGET_ID, expenseToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned budget should be the same as mock budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldDeleteExpense_ExpenseSetShouldCorectSize() {
        final Expense expenseToAdd = new Expense("Lekarz", 150.0, UUID.randomUUID(), ExpenseType.HEALTHCARE, new Date());
        stubRepositoryToDeleteExpense(expenseToAdd);
        final Budget returnedBudget = expenseService.deleteFinancialOperation(BUDGET_ID, expenseToAdd);

        verify(budgetRepository, times(1)).save(any(Budget.class));
        verify(budgetService, times(1)).getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT);
        assertEquals("Returned expense size should has correct size", returnedBudget.getExpenses().size(), 4);
    }

    private Budget stubRepositoryToAddNewExpense(Expense expenseToAdd) {
        final Budget budgetFromRepository = BudgetTestUtils.createBudgetEntityForTestExpensesAndIncome(USER_LOGIN);
        final Budget budgetToSave = BudgetTestUtils.createBudgeWithAdditionalExpense(budgetFromRepository, expenseToAdd);
        when(budgetService.getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT)).thenReturn(budgetFromRepository);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);
        return budgetToSave;
    }

    private Budget stubRepositoryToDeleteExpense(Expense expenseToAdd) {
        final Budget budgetToSave = BudgetTestUtils.createBudgetEntityForTestExpensesAndIncome(USER_LOGIN);
        final Budget budgetFromRepository = BudgetTestUtils.createBudgeWithAdditionalExpense(budgetToSave, expenseToAdd);

        when(budgetService.getOneById(BUDGET_ID, PermissionType.OWNER, PermissionType.EDIT)).thenReturn(budgetFromRepository);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);
        return budgetToSave;
    }

}
