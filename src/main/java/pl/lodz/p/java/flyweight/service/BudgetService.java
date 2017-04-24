package pl.lodz.p.java.flyweight.service;

import java.util.List;
import pl.lodz.p.java.flyweight.model.Budget;

public interface BudgetService {

    public Budget getOneBudgetEntity(long budgetId);

    public Budget saveBudgetEntity(Budget budget);

    public Budget getOneByUserIdAndOwner(long userID);

    public List<Budget> getSharedBudgets(long userID);
}
