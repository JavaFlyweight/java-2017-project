package pl.lodz.p.java.flyweight.service;

import java.util.List;
import java.util.UUID;

import pl.lodz.p.java.flyweight.model.Budget;

public interface BudgetService {

    public Budget getOneBudgetEntity(UUID budgetId);


    public Budget saveBudgetEntity(Budget budget);


    public Budget getOneByUserIdAndOwner(UUID userID);


    public List<Budget> getSharedBudgets(UUID userID);
}
