package pl.lodz.p.java.flyweight.repository;

import pl.lodz.p.java.flyweight.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
    
    public User findOneByEmail (String email);
}
