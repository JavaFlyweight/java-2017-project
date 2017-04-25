package pl.lodz.p.java.flyweight.repository;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoCustom {

    public void setMongoTemplate(MongoTemplate mongoTemplate);

}
