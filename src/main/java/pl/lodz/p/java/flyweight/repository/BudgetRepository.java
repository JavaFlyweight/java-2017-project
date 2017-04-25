package pl.lodz.p.java.flyweight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.lodz.p.java.flyweight.model.Budget;

public interface BudgetRepository extends MongoRepository<Budget, String>, BudgetRepositoryCustom {

}
