package pl.lodz.p.java.flyweight.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import pl.lodz.p.java.flyweight.exception.BudgetNotFoundException;
import pl.lodz.p.java.flyweight.model.Budget;
import pl.lodz.p.java.flyweight.model.Expense;
import pl.lodz.p.java.flyweight.model.Income;

import pl.lodz.p.java.flyweight.repository.BudgetRepository;

@Service
@Validated
public class BudgetServiceImpl implements BudgetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);

    private final BudgetRepository budgetRepository;


    @Inject
    public BudgetServiceImpl(final BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }


    @Override
    public Budget getOneBudgetEntity(UUID budgetId) {
        return budgetRepository.findOneById(budgetId);
    }


    @Override
    public Budget saveBudgetEntity(Budget budget) {
        return budgetRepository.save(budget);
    }


    @Override
    public Budget getOneByUserIdAndOwner(UUID userID) {
        return budgetRepository.findOneByUserIdAndOwner(userID);
    }


    public List<Budget> getSharedBudgets(UUID userID) {
        return budgetRepository.findOneByUserIdAndShared(userID);
    }


    public Budget addNewIncome(UUID budgetId, Income income) {
        Budget editedBudget = getOneBudgetEntity(budgetId);
        if (editedBudget == null) {
            throw new BudgetNotFoundException(budgetId);
        }
        Set<Income> incomes = editedBudget.getIncomes();
        incomes.add(income);
        editedBudget.setIncomes(incomes);
        budgetRepository.save(editedBudget);
        return editedBudget;
    }


    public Budget addNewExpense(UUID budgetId, Expense expense) {
        Budget editedBudget = getOneBudgetEntity(budgetId);
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
