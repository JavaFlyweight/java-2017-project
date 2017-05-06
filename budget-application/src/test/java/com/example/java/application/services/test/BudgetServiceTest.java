package com.example.java.application.services.test;

import com.example.java.application.services.BudgetService;
import com.example.java.application.services.BudgetServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exception.BudgetForbiddenAccessException;
import com.example.java.commons.exception.BudgetNotFoundException;
import com.example.java.commons.exception.UserAlreadyHasBudget;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Permission;
import com.example.java.repository.BudgetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserService userService;
    @InjectMocks
    private final BudgetService budgetService = new BudgetServiceImpl();

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceTest.class);

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final String USER_LOGIN = "abc@wp.pl";
    private static final String USER_LOGIN2 = "xyz@wp.pl";

    static Budget globalBudgetForCreateTest;

    @Test
    public void shouldGetOneBudgetEntityByIdAndViewPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.VIEW);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldGetOneBudgetEntityByIdAndOwnerPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.OWNER);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldGetOneBudgetEntityByIdAndEditPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.EDIT);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test(expected = BudgetForbiddenAccessException.class)
    public void shouldGetOneBudgetEntityByIdAndWithoutPermission_TheExceptionShouldBeThrown() {
        stubRepositoryToGetOneBudgetEntityByIdWithOtherUserPermission();
        budgetService.getOneById(BUDGET_ID);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
    }

    @Test
    public void shouldCreateBudgetEntity() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        LOGGER.info("resuly" + globalBudgetForCreateTest);
        assertEquals("Returned budget should be the same as repository budget", globalBudgetForCreateTest, returnedBudget);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckPermissionType() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission should be owner type", permissionsReturnedBudget.iterator().next().getPermissionType(), PermissionType.OWNER);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckUserLogin() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission should has correct user login", permissionsReturnedBudget.iterator().next().getUserLogin(), USER_LOGIN);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckPermissionsSize() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission set should has one element", permissionsReturnedBudget.size(), 1);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckExpensesIsEmpty() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Expense> expensesReturnedBudget = returnedBudget.getExpenses();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertTrue("Returned expenses set should be empty", expensesReturnedBudget.isEmpty());
    }

    @Test(expected = UserAlreadyHasBudget.class)
    public void shouldCreateBudgetEntity_TheExceptionShouldBeThrown() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntityWithNoEmptyPermission();
        budgetService.createBudgetEntity(budgetToCreate);

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
    }

    @Test
    public void shouldGetOneBudgetEntityByUserLoginAndOwner() {
        final Budget budgetFromRepository = stubRepositoryToCreateBudgetEntityWithNoEmptyPermission();
        final Budget returnedBudget = budgetService.getOneByUserLoginAndOwner();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test(expected = BudgetNotFoundException.class)
    public void shouldGetOneBudgetEntityByUserLoginAndOwner_TheExceptionShouldBeThrown() {
        Budget budgetToCreate = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        budgetService.getOneByUserLoginAndOwner();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
    }

    @Test
    public void shouldGetSharedBudgets_checkReturnedListSize() {
        stubRepositoryToGetSharedBudgets();
        final  List<Budget> returnedBudgets= budgetService.getSharedBudgets();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.EDIT);
        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.VIEW);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget list should has 5 element", returnedBudgets.size(), 5);
    }

    private Budget stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType permissionType) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, permissionType);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        return budgetFromRepository;
    }

    private void stubRepositoryToGetOneBudgetEntityByIdWithOtherUserPermission() {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2, PermissionType.OWNER);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
    }

    private void stubRepositoryToCreateBudgetEntity() {
        final List<Budget> emptyBudgetList = new ArrayList<>();
        when(budgetRepository.findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER)).thenReturn(emptyBudgetList);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenAnswer(returnFirstArgumentFromSaveMethod());

    }

    private static Answer<Object> returnFirstArgumentFromSaveMethod() {
        return (InvocationOnMock invocation) -> {
            globalBudgetForCreateTest = (Budget) invocation.getArguments()[0];
            return globalBudgetForCreateTest;
        };
    }

    private Budget stubRepositoryToCreateBudgetEntityWithNoEmptyPermission() {
        final List<Budget> noEmptyBudgetList = BudgetTestUtils.createListeBudget(USER_LOGIN, PermissionType.OWNER, 1);

        when(budgetRepository.findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER)).thenReturn(noEmptyBudgetList);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        return noEmptyBudgetList.get(0);
    }

    private void stubRepositoryToGetSharedBudgets() {
        final List<Budget> budgetsWithEditPermission = BudgetTestUtils.createListeBudget(USER_LOGIN, PermissionType.EDIT, 2);
        final List<Budget> budgetWithViewPermission = BudgetTestUtils.createListeBudget(USER_LOGIN, PermissionType.VIEW, 3);

        when(budgetRepository.findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.EDIT)).thenReturn(budgetsWithEditPermission);
        when(budgetRepository.findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.VIEW)).thenReturn(budgetWithViewPermission);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
    }

}
