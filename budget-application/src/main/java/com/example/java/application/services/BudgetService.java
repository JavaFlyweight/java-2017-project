package com.example.java.application.services;

import com.example.java.commons.enums.PermissionType;
import java.util.List;
import java.util.UUID;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.BudgetCreateRequest;
import com.example.java.domain.model.BudgetEditRequest;

public interface BudgetService {

    public Budget getOneById(UUID budgetId, PermissionType... permissionTypes);

    public Budget createBudgetEntity(BudgetCreateRequest dataToCreateBudget);
    
    public Budget getOneByUserLoginAndOwner();

    public List<Budget> getAllByUserLoginAndOwner();

    public List<Budget> getSharedBudgets();

    public Budget editBudgetEntity(UUID budgetId, BudgetEditRequest dataToEditBudget);
    
    public void deleteBudgetEntity(UUID budgetId);

    public Budget shareBudget(UUID budgetId, String userLogin, PermissionType permissionType);
    
    public Budget unshareBudget(UUID budgetId, String userLoginToRemovePermision);
    
    public Budget copyBudget(UUID budgetId);
}
