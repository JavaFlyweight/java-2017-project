package pl.lodz.p.java.flyweight.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import pl.lodz.p.java.flyweight.model.User;
import pl.lodz.p.java.flyweight.utils.QueryUtils;

public class UserRepositoryImpl implements MongoCustom, UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public User findOneByUserId(UUID id) {
        return mongoTemplate.findById(id, User.class);
    }


    @Override
    public User findOneByEmailAndPassword(String email, String password) {
        return mongoTemplate.findOne(QueryUtils.queryFindOneUserByEmailAndPassword(email, password), User.class);
    }

}
