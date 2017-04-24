
package pl.lodz.p.java.flyweight.repository;

import java.util.List;
import pl.lodz.p.java.flyweight.model.Budget;


public interface BudgetRepositoryCustom {
     public Budget findOneById(long id);
     public Budget findOneByUserIdAndOwner(long userId);
     public List<Budget> findOneByUserIdAndShared(long userId);
}
