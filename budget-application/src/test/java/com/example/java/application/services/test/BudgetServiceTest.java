package com.example.java.application.services.test;

import com.example.java.application.services.BudgetService;
import com.example.java.application.services.BudgetServiceImpl;
import com.example.java.application.services.UserService;
import com.example.java.application.services.UtilsService;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.commons.exceptions.BudgetNotFoundException;
import com.example.java.commons.exceptions.PermissionIsAddedAlreadyException;
import com.example.java.commons.exceptions.PermissionNotFoundException;
import com.example.java.commons.exceptions.UserAlreadyHasBudget;
import com.example.java.commons.exceptions.WrongPermissionTypeException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.BudgetCreateRequest;
import com.example.java.domain.model.BudgetEditRequest;
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
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import static org.mockito.Matchers.any;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilsService.class)
public class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private final BudgetService budgetService = new BudgetServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceTest.class);

    private static final UUID BUDGET_ID = UUID.randomUUID();
    private static final String USER_LOGIN = "abc@wp.pl";
    private static final String USER_LOGIN2 = "xyz@wp.pl";

    static Budget globalBudgetForCreateTest;
    static Budget globalBudgetForEditTest;

    @Test
    public void shouldGetOneBudgetEntityByIdAndViewPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.VIEW, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldGetOneBudgetEntityByIdAndOwnerPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.OWNER, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldGetOneBudgetEntityByIdAndEditPermission() {
        final Budget budgetFromRepository = stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType.EDIT, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);
        final Budget returnedBudget = budgetService.getOneById(BUDGET_ID, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test(expected = BudgetNotFoundException.class)
    public void shouldGetOneBudgetEntityById_TheExceptionShouldBeThrown() {
        stubRepositoryToGetNullBudgetEntity();
        budgetService.getOneById(BUDGET_ID, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
    }

    @Test(expected = BudgetForbiddenAccessException.class)
    public void shouldGetOneBudgetEntityByIdAndWithoutPermission_TheExceptionShouldBeThrown() {
        stubRepositoryToGetOneBudgetEntityByIdWithOtherUserPermission(PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);
        budgetService.getOneById(BUDGET_ID, PermissionType.OWNER,
                PermissionType.EDIT, PermissionType.VIEW);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
    }

    @Test
    public void shouldCreateBudgetEntity() {
        BudgetCreateRequest budgetToCreate = BudgetTestUtils.createBudgetCreateRequestEntity(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);

        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned budget should be the same as repository budget", globalBudgetForCreateTest, returnedBudget);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckPermissionType() {
        BudgetCreateRequest budgetToCreate = BudgetTestUtils.createBudgetCreateRequestEntity(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission should be owner type", permissionsReturnedBudget.iterator().next().getPermissionType(), PermissionType.OWNER);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckUserLogin() {
        BudgetCreateRequest budgetToCreate = BudgetTestUtils.createBudgetCreateRequestEntity(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission should has correct user login", permissionsReturnedBudget.iterator().next().getUserLogin(), USER_LOGIN);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckPermissionsSize() {
        BudgetCreateRequest budgetToCreate = BudgetTestUtils.createBudgetCreateRequestEntity(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Permission> permissionsReturnedBudget = returnedBudget.getPermissions();

        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned permission set should has one element", permissionsReturnedBudget.size(), 1);
    }

    @Test
    public void shouldCreateBudgetEntityWithOwnerPermission_CheckExpensesIsEmpty() {
        BudgetCreateRequest budgetToCreate = BudgetTestUtils.createBudgetCreateRequestEntity(USER_LOGIN, PermissionType.OWNER);
        stubRepositoryToCreateBudgetEntity();
        final Budget returnedBudget = budgetService.createBudgetEntity(budgetToCreate);
        Set<Expense> expensesReturnedBudget = returnedBudget.getExpenses();

        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertTrue("Returned expenses set should be empty", expensesReturnedBudget.isEmpty());
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
        stubRepositoryToCreateBudgetEntity();
        budgetService.getOneByUserLoginAndOwner();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.OWNER);
        verify(userService, times(2)).getLoggedUserLogin();
    }

    @Test
    public void shouldGetSharedBudgets_checkReturnedListSize() {
        stubRepositoryToGetSharedBudgets();
        final List<Budget> returnedBudgets = budgetService.getSharedBudgets();

        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.EDIT);
        verify(budgetRepository, times(1)).findAllByUserLoginAndPermission(USER_LOGIN, PermissionType.VIEW);
        verify(userService, times(1)).getLoggedUserLogin();
        assertEquals("Returned budget list should has 5 element", returnedBudgets.size(), 5);
    }

    @Test
    public void shouldEditBudgetEntity_PlannedAmountShouldBeSet() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned plannedAmount should be the same as in dataToEdit", dataToEdit.getPlannedAmount(), returnedBudget.getPlannedAmount(), 0.01);
    }

    @Test
    public void shouldEditBudgetEntity_BalanceShouldNotBeSet() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        final Budget repositoryBudget = stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertNotSame("Returned balance should not be the same as in dataToEdit", repositoryBudget.getBalance(), returnedBudget.getBalance());
    }

    @Test
    public void shouldEditBudgetEntity_BalanceShouldBeNotChange() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        final Budget budgetFromRepository = stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned balance should be the same as balance from repository", budgetFromRepository.getBalance(), returnedBudget.getBalance(), 0.01);
    }

    @Test
    public void shouldEditBudgetEntity_DateFromShouldBeSet() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned dateFrom should be the same as in dataToEdit", dataToEdit.getDateFrom(), returnedBudget.getDateFrom());
    }

    @Test
    public void shouldEditBudgetEntity_DateToShouldBeSet() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned dateTo should be the same as in dataToEdit", dataToEdit.getDateTo(), returnedBudget.getDateTo());
    }

    @Test
    public void shouldEditBudgetEntity_NameShouldBeSet() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        stubRepositoryToEditBudget(PermissionType.OWNER, PermissionType.OWNER, PermissionType.EDIT);
        final Budget returnedBudget = budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned name should be the same as in dataToEdit", dataToEdit.getName(), returnedBudget.getName());
    }

    @Test(expected = BudgetForbiddenAccessException.class)
    public void shouldEditBudgetEntity_TheExceptionShouldBeThrown() {
        BudgetEditRequest dataToEdit = BudgetTestUtils.createOneBudgetDataToEditWithId();
        stubRepositoryToEditBudgetFalsePermission(PermissionType.VIEW, PermissionType.OWNER, PermissionType.EDIT);
        budgetService.editBudgetEntity(BUDGET_ID, dataToEdit);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
    }

    private Budget stubRepositoryToGetOneBudgetEntityByIdWithViewPermission(PermissionType permissionType, PermissionType... permissionTypes) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, permissionType);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, permissionTypes)).thenReturn(true);
        return budgetFromRepository;
    }

    @Test
    public void shouldShareBudgetEntity() {
        final Budget budgetFromRepository = stubRepositoryToShareBudget();
        final Budget returnedBudget = budgetService.shareBudget(BUDGET_ID, USER_LOGIN, PermissionType.EDIT);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test(expected = WrongPermissionTypeException.class)
    public void shouldShareBudgetEntityWhereTryAddOwnerPermission_TheExceptionShouldBeThrown() {
        stubRepositoryToShareBudget();
        budgetService.shareBudget(BUDGET_ID, USER_LOGIN, PermissionType.OWNER);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test(expected = PermissionIsAddedAlreadyException.class)
    public void shouldShareBudgetEntityWhereUserHasThisPermission_TheExceptionShouldBeThrown() {
        final Budget budgetFromRepository = stubRepositoryToShareBudgetWithEditPermission();
        final Budget returnedBudget = budgetService.shareBudget(BUDGET_ID, USER_LOGIN2, PermissionType.EDIT);
        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test
    public void shouldUnshareBudgetEntity() {
        final Budget budgetFromRepository = stubRepositoryToUnshareBudget();
        final Budget returnedBudget = budgetService.unshareBudget(BUDGET_ID, USER_LOGIN2);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
        assertEquals("Returned budget should be the same as repository budget", budgetFromRepository, returnedBudget);
    }

    @Test(expected = PermissionNotFoundException.class)
    public void shouldUnshareBudgetEntity_TheExceptionShouldBeThrown() {
        stubRepositoryToUnshareBudgetWithoutPermission();
        budgetService.unshareBudget(BUDGET_ID, USER_LOGIN2);

        verify(budgetRepository, times(1)).findOneById(BUDGET_ID);
        verify(userService, times(1)).getLoggedUserLogin();
        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    private void stubRepositoryToGetNullBudgetEntity() {
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(null);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
    }

    private void stubRepositoryToGetOneBudgetEntityByIdWithOtherUserPermission(PermissionType... permissionTypes) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN2, PermissionType.OWNER);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, permissionTypes)).thenReturn(false);
    }

    private void stubRepositoryToCreateBudgetEntity() {
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenAnswer(returnFirstArgumentFromSaveMethodForCreateMethod());
    }

    private static Answer<Object> returnFirstArgumentFromSaveMethodForCreateMethod() {
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

    private Budget stubRepositoryToEditBudget(PermissionType permissionType, PermissionType... permissionTypes) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, permissionType);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenAnswer(returnFirstArgumentFromSaveMethodForEditMethod());

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, permissionTypes)).thenReturn(true);
        return budgetFromRepository;
    }

    private Budget stubRepositoryToEditBudgetFalsePermission(PermissionType permissionType, PermissionType... permissionTypes) {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, permissionType);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenAnswer(returnFirstArgumentFromSaveMethodForEditMethod());

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, permissionTypes)).thenReturn(false);
        return budgetFromRepository;
    }

    private static Answer<Object> returnFirstArgumentFromSaveMethodForEditMethod() {
        return (InvocationOnMock invocation) -> {
            globalBudgetForEditTest = (Budget) invocation.getArguments()[0];
            return globalBudgetForEditTest;
        };
    }

    private Budget stubRepositoryToShareBudget() {
        final Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        final Budget budgetToSave = BudgetTestUtils.addPermissionToBudget(budgetFromRepository, USER_LOGIN2, PermissionType.EDIT);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, PermissionType.OWNER)).thenReturn(true);
        return budgetToSave;
    }

    private Budget stubRepositoryToShareBudgetWithEditPermission() {
        Budget budgetFromRepository = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        budgetFromRepository = BudgetTestUtils.addPermissionToBudget(budgetFromRepository, USER_LOGIN2, PermissionType.EDIT);
        final Budget budgetToSave = BudgetTestUtils.addPermissionToBudget(budgetFromRepository, USER_LOGIN2, PermissionType.EDIT);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, PermissionType.OWNER)).thenReturn(true);
        return budgetToSave;
    }

    private Budget stubRepositoryToUnshareBudget() {
        final Budget budgetToSave = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        final Budget budgetFromRepository = BudgetTestUtils.addPermissionToBudget(budgetToSave, USER_LOGIN2, PermissionType.EDIT);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetFromRepository);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetFromRepository, USER_LOGIN, PermissionType.OWNER)).thenReturn(true);
        return budgetToSave;
    }

    private Budget stubRepositoryToUnshareBudgetWithoutPermission() {
        final Budget budgetToSave = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        when(budgetRepository.findOneById(BUDGET_ID)).thenReturn(budgetToSave);
        when(userService.getLoggedUserLogin()).thenReturn(USER_LOGIN);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budgetToSave);

        PowerMockito.mockStatic(UtilsService.class);
        PowerMockito.when(UtilsService.checkPermissionForBudget(budgetToSave, USER_LOGIN, PermissionType.OWNER)).thenReturn(true);
        return budgetToSave;
    }

}