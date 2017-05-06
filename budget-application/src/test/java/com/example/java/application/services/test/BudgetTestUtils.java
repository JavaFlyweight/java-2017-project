package com.example.java.application.services.test;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BudgetTestUtils {

    public static Budget createOneBudgetEntityByIdWithPermission(String userLogin, PermissionType permissionType) {
        final Budget createdBudget = new Budget(12.03, 1234.76, new Date(), new Date());
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(userLogin, permissionType));
        createdBudget.setPermissions(permissions);
        Set<Expense> expenses = new HashSet<>();
        expenses.add(new Expense("Mieszkanie", 30.9, null));
        createdBudget.setExpenses(expenses);
        return createdBudget;
    }
    public static List<Budget> createListeBudget(String userLogin, PermissionType permissionType, int size){
        final List<Budget> listBudgetToCreate = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listBudgetToCreate.add(createOneBudgetEntityByIdWithPermission(userLogin, permissionType));
        }   
        return listBudgetToCreate;
    }
}
