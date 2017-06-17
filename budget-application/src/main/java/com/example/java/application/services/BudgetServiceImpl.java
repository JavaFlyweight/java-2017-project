package com.example.java.application.services;

import static com.example.java.application.services.UtilsService.checkPermissionForBudget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exceptions.BudgetForbiddenAccessException;
import com.example.java.commons.exceptions.BudgetNotFoundException;
import com.example.java.commons.exceptions.PermissionIsAddedAlreadyException;
import com.example.java.commons.exceptions.PermissionNotFoundException;
import com.example.java.commons.exceptions.WrongDateValue;
import com.example.java.commons.exceptions.WrongPermissionTypeException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.BudgetCreateRequest;
import com.example.java.domain.model.BudgetEditRequest;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.domain.model.Permission;
import com.example.java.repository.BudgetRepository;
import java.util.Date;

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
        setAddedByUserEmailForBudget(budgetToReturn);

        return budgetToReturn;
    }

    private void setAddedByUserEmailForBudget(Budget budgetToReturn) {
        Set<Expense> expenses = budgetToReturn.getExpenses();
        Set<Income> incomes = budgetToReturn.getIncomes();
        incomes.stream().forEach((Income income) -> {
            if (income.getAddedBy() != null) {
                income.setAddedByUserEmail(userService.getUser(income.getAddedBy()).getEmail());
            }
        });
        expenses.stream().forEach((Expense expense) -> {
            if (expense.getAddedBy() != null) {
                expense.setAddedByUserEmail(userService.getUser(expense.getAddedBy()).getEmail());
            }
        });
        budgetToReturn.setIncomes(incomes);
        budgetToReturn.setExpenses(expenses);
    }

    @Override
    public Budget createBudgetEntity(BudgetCreateRequest dataToCreateBudget) {
        validateDateForBudget(dataToCreateBudget);
        Budget budgetToSave = new Budget(dataToCreateBudget.getName(), dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(),
                dataToCreateBudget.getDateTo(), dataToCreateBudget.getImage());
        Set<Permission> permissions = new HashSet<>();
        String userLogin = userService.getLoggedUserLogin();
        permissions.add(new Permission(userLogin, PermissionType.OWNER));
        budgetToSave.setPermissions(permissions);
        setAddedByUserEmailForBudget(budgetToSave);
        return budgetRepository.save(budgetToSave);
    }

    private void validateDateForBudget(BudgetEditRequest budgetToValidate) {
        if (budgetToValidate.getDateTo().before(budgetToValidate.getDateFrom())) {
            throw new WrongDateValue(budgetToValidate.getDateFrom(), budgetToValidate.getDateTo());
        }
        if (budgetToValidate.getDateTo().before(new Date())) {
            throw new WrongDateValue(budgetToValidate.getDateTo());
        }
    }

    @Override
    public Budget getOneByUserLoginAndOwner() {
        List<Budget> allBudgetByUserLoginAndOwner = getAllByUserLoginAndOwner();
        if (allBudgetByUserLoginAndOwner.isEmpty()) {
            throw new BudgetNotFoundException();
        }
        Budget budgetToReturn = allBudgetByUserLoginAndOwner.get(0);
        setAddedByUserEmailForBudget(budgetToReturn);
        return budgetToReturn;
    }

    @Override
    public List<Budget> getAllByUserLoginAndOwner() {
        String userLogin = userService.getLoggedUserLogin();
        List<Budget> foundBudgets = budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.OWNER);
        foundBudgets.stream().forEach((foundBudget) -> {
            setAddedByUserEmailForBudget(foundBudget);
        });
        return foundBudgets;
    }

    @Override
    public List<Budget> getSharedBudgets() {
        String userLogin = userService.getLoggedUserLogin();
        List<Budget> sharedBudgets = new ArrayList<>();
        sharedBudgets.addAll(budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.EDIT));
        sharedBudgets.addAll(budgetRepository.findAllByUserLoginAndPermission(userLogin, PermissionType.VIEW));
        sharedBudgets.stream().forEach((sharedBudget) -> {
            setAddedByUserEmailForBudget(sharedBudget);
        });
        return sharedBudgets;
    }

    @Override
    public Budget editBudgetEntity(UUID budgetId, BudgetEditRequest dataToEditBudget) {
        validateDateForBudget(dataToEditBudget);
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
                budgetToUnshare.setPermissions(listToRemovePermission);
                return budgetRepository.save(budgetToUnshare);
            }
            permissions = listToRemovePermission;
        }
        throw new PermissionNotFoundException(userLoginToRemovePermision, budgetId);
    }

    @Override
    public Budget copyBudget(UUID budgetId) {
        Budget budgetToCloneBudget = getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT);
        Budget cloneBudget = new Budget(budgetToCloneBudget);
        Set<Permission> permissions = new HashSet<>();
        String userLogin = userService.getLoggedUserLogin();
        permissions.add(new Permission(userLogin, PermissionType.OWNER));
        cloneBudget.setPermissions(permissions);
        return budgetRepository.save(cloneBudget);
    }
}
