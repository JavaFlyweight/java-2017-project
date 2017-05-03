package com.example.java.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.exception.BudgetNotFoundException;
import com.example.java.commons.exception.BudgetForbiddenAccessException;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.domain.model.Permission;
import com.example.java.repository.BudgetRepository;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BudgetServiceImpl implements BudgetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

    @Override
    public Budget getOneBudgetEntityToEdit(UUID budgetId) {
        String login = userService.getLoggedUserName();
        LOGGER.debug("Log user is  {0}", new Object[]{login});
        Budget budgetToReturn = budgetRepository.findOneById(budgetId);

        if (!checkPermissionForBudgetOwnerAndEdit(budgetToReturn, login)) {
            throw new BudgetForbiddenAccessException(budgetId);
        }
        return budgetToReturn;
    }

    @Override
    public Budget getOneBudgetEntityToView(UUID budgetId) {
        String login = userService.getLoggedUserName();
        LOGGER.debug("Log user is  {0}", new Object[]{login});
        Budget budgetToReturn = budgetRepository.findOneById(budgetId);

        if (checkPermissionForBudgetViewRoles(budgetToReturn, login)) {
            throw new BudgetForbiddenAccessException(budgetId);
        }
        return budgetToReturn;
    }
    
        @Override
    public Budget createBudgetEntity(Budget dataToCreateBudget) {
        Budget budgetToSave=new Budget(dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(),dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo());
        Set<Permission> permissions = new HashSet<>();
        String login = userService.getLoggedUserName();
        LOGGER.debug("Log user is  {0}", new Object[]{login});
        permissions.add(new Permission(login, PermissionType.OWNER));
        return budgetRepository.save(budgetToSave);
    }

    @Override
    public Budget saveBudgetEntity(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getAllByUserIdAndOwner(UUID userID) {
        return budgetRepository.findAllByIdAndPermissions(userID, PermissionType.OWNER);
    }

    @Override
    public List<Budget> getSharedBudgets(UUID userID) {
        List<Budget> result = new ArrayList<>();
        result.addAll(budgetRepository.findAllByIdAndPermissions(userID, PermissionType.EDIT));
        result.addAll(budgetRepository.findAllByIdAndPermissions(userID, PermissionType.VIEW));

        return result;
    }

    @Override
    public Budget addNewIncome(UUID budgetId, Income income) {
        Budget editedBudget = getOneBudgetEntityToEdit(budgetId);

        if (editedBudget == null) {
            throw new BudgetNotFoundException(budgetId);
        }

        Set<Income> incomes = editedBudget.getIncomes();
        incomes.add(income);

        editedBudget.setIncomes(incomes);

        budgetRepository.save(editedBudget);

        return editedBudget;
    }

    @Override
    public Budget addNewExpense(UUID budgetId, Expense expense) {
        Budget editedBudget = getOneBudgetEntityToEdit(budgetId);

        if (editedBudget == null) {
            throw new BudgetNotFoundException(budgetId);
        }

        Set<Expense> expenses = editedBudget.getExpenses();
        expenses.add(expense);

        editedBudget.setExpenses(expenses);

        budgetRepository.save(editedBudget);

        return editedBudget;
    }

    private boolean checkPermissionForBudgetOnlyOwner(Budget budgetToCheck, String userName) {
        Set<Permission> permissions = budgetToCheck.getPermissions();
        for (Permission permission : permissions) {
            if (PermissionType.OWNER.equals(permission.getPermissionType()) && userName.equals(permission.getUserName())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPermissionForBudgetOwnerAndEdit(Budget budgetToCheck, String userName) {
        Set<Permission> permissions = budgetToCheck.getPermissions();
        for (Permission permission : permissions) {
            if ((PermissionType.OWNER.equals(permission.getPermissionType()) && userName.equals(permission.getUserName())) || (PermissionType.EDIT.equals(permission.getPermissionType()) && userName.equals(permission.getUserName()))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPermissionForBudgetViewRoles(Budget budgetToCheck, String userName) {
        Set<Permission> permissions = budgetToCheck.getPermissions();
        for (Permission permission : permissions) {
            if ((PermissionType.OWNER.equals(permission.getPermissionType()) && userName.equals(permission.getUserName())) || (PermissionType.EDIT.equals(permission.getPermissionType()) && userName.equals(permission.getUserName())) || (PermissionType.VIEW.equals(permission.getPermissionType()) && userName.equals(permission.getUserName()))) {
                return true;
            }
        }
        return false;
    }

}
