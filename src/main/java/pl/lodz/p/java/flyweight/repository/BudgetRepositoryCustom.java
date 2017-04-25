
package pl.lodz.p.java.flyweight.repository;

import java.util.List;
import java.util.UUID;

import pl.lodz.p.java.flyweight.model.Budget;

public interface BudgetRepositoryCustom {

    public Budget findOneById(UUID id);


    public Budget findOneByUserIdAndOwner(UUID userId);


    public List<Budget> findOneByUserIdAndShared(UUID userId);

}
