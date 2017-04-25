package pl.lodz.p.java.flyweight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.lodz.p.java.flyweight.model.User;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

}
