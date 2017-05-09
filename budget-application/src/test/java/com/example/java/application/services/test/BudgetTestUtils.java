package com.example.java.application.services.test;

import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.domain.model.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BudgetTestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetTestUtils.class);

    public static Budget createOneBudgetEntityByIdWithPermission(String userLogin, PermissionType permissionType) {
        Date date = new Date();
        date.setTime(new Date().getTime() + 11 * 24 * 60 * 60 * 1000);
        final Budget createdBudget = new Budget(12.03, 1234.76, new Date(), date);
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(userLogin, permissionType));
        createdBudget.setPermissions(permissions);
        Set<Expense> expenses = new HashSet<>();
        expenses.add(new Expense("Mieszkanie", 30.9, null, ExpenseType.HOUSEHOLD));
        createdBudget.setExpenses(expenses);
        Set<Income> incomes = new HashSet<>();
        incomes.add(new Income("Premia", 1000.99, null, IncomeType.PREMIUM));
        createdBudget.setIncomes(incomes);
        return createdBudget;
    }

    public static Budget createOneBudgetDataToEditWithId(UUID budgetID) {
        Date date = new Date();
        date.setTime(new Date().getTime() + 11 * 24 * 60 * 3 * 60 * 1000);
        Date date2 = new Date();
        date2.setTime(new Date().getTime() + 22 * 24 * 60 * 60 * 1000);
        final Budget dataToEdit = new Budget(24.03, 4321.76, date, date2);
        dataToEdit.setId(budgetID);
        return dataToEdit;
    }

    public static List<Budget> createListeBudget(String userLogin, PermissionType permissionType, int size) {
        final List<Budget> listBudgetToCreate = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listBudgetToCreate.add(createOneBudgetEntityByIdWithPermission(userLogin, permissionType));
        }
        return listBudgetToCreate;
    }
}
