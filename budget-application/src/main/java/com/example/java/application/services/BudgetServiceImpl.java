package com.example.java.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.commons.exceptions.BudgetNotFoundException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Permission;
import com.example.java.repository.BudgetRepository;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.java.application.services.UtilsService.checkPermissionForBudget;
import com.example.java.commons.exceptions.PermissionIsAddedAlreadyException;
import com.example.java.commons.exceptions.PermissionNotFoundException;
import com.example.java.commons.exceptions.WrongPermissionTypeException;
import com.example.java.domain.model.BudgetCreateRequest;
import com.example.java.domain.model.BudgetEditRequest;
import java.util.Iterator;

@Service
public class BudgetServiceImpl implements BudgetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

    @Override
    public Budget getOneById(UUID budgetId, PermissionType... permissionTypes) {
        String userLogin = userService.getLoggedUserLogin();
        Budget budgetToReturn = budgetRepository.findOneById(budgetId);
        if (budgetToReturn == null) {
            throw new BudgetNotFoundException(budgetId);
        }

        if (!checkPermissionForBudget(budgetToReturn, userLogin, permissionTypes)) {
            throw new BudgetForbiddenAccessException(budgetId);
        }
        return budgetToReturn;
    }

    @Override
    public Budget createBudgetEntity(BudgetCreateRequest dataToCreateBudget) {
        Budget budgetToSave = new Budget(dataToCreateBudget.getName(), dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo(), dataToCreateBudget.getImage());
        Set<Permission> permissions = new HashSet<>();
        String userLogin = userService.getLoggedUserLogin();
        permissions.add(new Permission(userLogin, PermissionType.OWNER));
        budgetToSave.setPermissions(permissions);
        return budgetRepository.save(budgetToSave);
    }

    @Override
    public Budget getOneByUserLoginAndOwner() {
        List<Budget> allBudgetByUserLoginAndOwner = getAllByUserLoginAndOwner();
        if (allBudgetByUserLoginAndOwner.isEmpty()) {
            throw new BudgetNotFoundException();
        }
        return allBudgetByUserLoginAndOwner.get(0);
    }

    private List<Budget> getAllByUserLoginAndOwner() {
        String userLogin = userService.getLoggedUserLogin();
        List<Budget> foundBudgets = budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.OWNER);
        return foundBudgets;
    }

    @Override
    public List<Budget> getSharedBudgets() {
        String userLogin = userService.getLoggedUserLogin();
        List<Budget> result = new ArrayList<>();
        result.addAll(budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.EDIT));
        result.addAll(budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.VIEW));

        return result;
    }

    @Override
    public Budget editBudgetEntity(UUID budgetId, BudgetEditRequest dataToEditBudget) {
        Budget budgetToEdit = getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        budgetToEdit.setPlannedAmount(dataToEditBudget.getPlannedAmount());
        budgetToEdit.setDateFrom(dataToEditBudget.getDateFrom());
        budgetToEdit.setDateTo(dataToEditBudget.getDateTo());
        budgetToEdit.setName(dataToEditBudget.getName());
        budgetToEdit.setImage(dataToEditBudget.getImage());
        return budgetRepository.save(budgetToEdit);
    }

    @Override
    public void deleteBudgetEntity(UUID budgetId) {
        Budget budgetToDelete = getOneById(budgetId, PermissionType.OWNER);
        budgetRepository.delete(budgetToDelete);
    }

    @Override
    public Budget shareBudget(UUID budgetId, String userLoginWithWillGetPermission, PermissionType permissionType) {
        Budget budgetToShare = getOneById(budgetId, PermissionType.OWNER);
        if (!permissionType.equals(PermissionType.EDIT) && !permissionType.equals(PermissionType.VIEW)) {
            throw new WrongPermissionTypeException(permissionType);
        }
        Set<Permission> permissions = budgetToShare.getPermissions();
        for (Permission permission : permissions) {
            Set<Permission> listToRemovePermission = new HashSet<>(permissions);
            if (permission.getUserLogin().equals(userLoginWithWillGetPermission)) {
                if (permission.getPermissionType().equals(permissionType)) {
                    throw new PermissionIsAddedAlreadyException(userLoginWithWillGetPermission, budgetId);
                } else {
                    listToRemovePermission.remove(permission);
                }
            }
            permissions = listToRemovePermission;
        }

        permissions.add(new Permission(userLoginWithWillGetPermission, permissionType));
        budgetToShare.setPermissions(permissions);
        return budgetRepository.save(budgetToShare);
    }

    @Override
    public Budget unshareBudget(UUID budgetId, String userLoginToRemovePermision) {
        Budget budgetToUnshare = getOneById(budgetId, PermissionType.OWNER);
        Set<Permission> permissions = budgetToUnshare.getPermissions();
        for (Permission permission : permissions) {
            Set<Permission> listToRemovePermission = new HashSet<>(permissions);
            if (permission.getUserLogin().equals(userLoginToRemovePermision)) {
                listToRemovePermission.remove(permission);
                return budgetRepository.save(budgetToUnshare);
            }
            permissions = listToRemovePermission;
        }
        throw new PermissionNotFoundException(userLoginToRemovePermision, budgetId);
    }
}
