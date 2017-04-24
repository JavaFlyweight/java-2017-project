package pl.lodz.p.java.flyweight.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.lodz.p.java.flyweight.model.Budget;
import pl.lodz.p.java.flyweight.utils.QueryUtils;

public class BudgetRepositoryImpl implements MongoCustom, BudgetRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Budget findOneById(long id) {
        return mongoTemplate.findById(id, Budget.class);
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Budget findOneByUserIdAndOwner(long userId) {
        return mongoTemplate.findOne(
                QueryUtils.queryFindOneByUserIdAndOwner(userId),
                Budget.class);
    }

    @Override
    public List<Budget> findOneByUserIdAndShared(long userId) {
        return mongoTemplate.find(
                QueryUtils.queryFindOneByUserIdAndShared(userId),
                Budget.class);
    }
}
