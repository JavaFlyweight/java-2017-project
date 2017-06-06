package com.example.java.application.services.test;

import com.example.java.application.services.UtilsService;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UtilsServiceTest {
    private static final String USER_LOGIN = "abc@wp.pl";
    private static final String USER_LOGIN2 = "xyz@wp.pl";

    @Test
    public void shouldCheckPermissionForBudgetForOwner() {
        final Budget budgetToCheck = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        assertTrue("Owner should have permission", UtilsService.checkPermissionForBudget(budgetToCheck, USER_LOGIN, PermissionType.OWNER, PermissionType.EDIT, PermissionType.VIEW));
    }

    @Test
    public void shouldCheckPermissionForBudgetForViever() {
        final Budget budgetToCheck = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.VIEW);
        assertFalse("Viever should not have permission", UtilsService.checkPermissionForBudget(budgetToCheck, USER_LOGIN, PermissionType.OWNER, PermissionType.EDIT));
    }

    @Test
    public void shouldCheckPermissionForBudgetForEditor() {
        final Budget budgetToCheck = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.EDIT);
        assertFalse("Editor should not have permission", UtilsService.checkPermissionForBudget(budgetToCheck, USER_LOGIN, PermissionType.OWNER));
    }

    @Test
    public void shouldCheckPermissionForBudgetForOtherUser() {
        final Budget budgetToCheck = BudgetTestUtils.createOneBudgetEntityByIdWithPermission(USER_LOGIN, PermissionType.OWNER);
        assertFalse("Other user should not have permission", UtilsService.checkPermissionForBudget(budgetToCheck, USER_LOGIN2, PermissionType.OWNER, PermissionType.EDIT, PermissionType.VIEW));
    }

}
