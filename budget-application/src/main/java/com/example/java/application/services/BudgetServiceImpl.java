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
import com.example.java.domain.model.Expense;
import com.example.java.domain.model.Income;
import com.example.java.domain.model.Permission;
import com.example.java.repository.BudgetRepository;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.java.application.services.UtilsService.checkPermissionForBudget;

@Service
public class BudgetServiceImpl implements BudgetService {


    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

    @Override
    public Budget getOneById(UUID budgetId) {
        String userLogin = userService.getLoggedUserLogin();
        Budget budgetToReturn = budgetRepository.findOneById(budgetId);

        if (!checkPermissionForBudget(budgetToReturn, userLogin,PermissionType.VIEW)) {
            throw new BudgetForbiddenAccessException(budgetId);
        }
        return budgetToReturn;
    }

    @Override
    public Budget createBudgetEntity(Budget dataToCreateBudget) {
        Budget budgetToSave = new Budget(dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo());
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
    public Budget editBudgetEntity(Budget dataToEditBudget) {
        //TODO check id is not epty
        Budget budgetToEdit = getOneByIdEdit(dataToEditBudget.getId());
        budgetToEdit.setPlannedAmount(dataToEditBudget.getPlannedAmount());
        budgetToEdit.setDateFrom(dataToEditBudget.getDateFrom());
        budgetToEdit.setDateTo(dataToEditBudget.getDateTo());
        return budgetRepository.save(budgetToEdit);
    }

    //TODO metoda pomocnicza - do usinięcia i zrefaktowryzowania po marge ze zmianami Bartka (przejście na UtilsService)
    private Budget getOneByIdEdit(UUID budgetId) {
        String userLogin = userService.getLoggedUserLogin();
        Budget budgetToReturn = budgetRepository.findOneById(budgetId);
        if (!checkPermissionForBudget(budgetToReturn, userLogin,PermissionType.OWNER,PermissionType.EDIT)) {
            throw new BudgetForbiddenAccessException(budgetId);
        }
        return budgetToReturn;
    }

    @Override
    public Budget addNewIncome(UUID budgetId, Income income) {
        Budget editedBudget = getOneById(budgetId);

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
        Budget editedBudget = getOneById(budgetId);

        if (editedBudget == null) {
            throw new BudgetNotFoundException(budgetId);
        }

        Set<Expense> expenses = editedBudget.getExpenses();
        expenses.add(expense);

        editedBudget.setExpenses(expenses);

        budgetRepository.save(editedBudget);

        return editedBudget;
    }


}
