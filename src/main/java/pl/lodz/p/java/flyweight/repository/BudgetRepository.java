package pl.lodz.p.java.flyweight.repository;

import java.util.List;
import pl.lodz.p.java.flyweight.model.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BudgetRepository extends MongoRepository<Budget, String>,  BudgetRepositoryCustom {
    
    @Override
    List<Budget> findAll();
}
