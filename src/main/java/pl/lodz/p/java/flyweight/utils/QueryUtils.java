package pl.lodz.p.java.flyweight.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.lodz.p.java.flyweight.model.PermissionType;

public class QueryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryUtils.class);

    public static final String PERMISSIONS = "permissions";

    public static final String USRER_ID = "userId";

    public static final String TYPE = "type";


    public static Query queryFindOneBudgetByUserIdAndOwner(UUID userId) {
        Query query = new Query();

        query.addCriteria(Criteria.where(PERMISSIONS).elemMatch(Criteria.where(USRER_ID).is(userId).and(TYPE).is(PermissionType.OWNER.toString())));

        LOGGER.info(query.getQueryObject().toString());
        return query;
    }


    public static Query queryFindOneBudgetByUserIdAndShared(UUID userId) {
        Query query = new Query();

        query.addCriteria(new Criteria().orOperator(Criteria.where(PERMISSIONS).elemMatch(Criteria.where(TYPE).is(PermissionType.VIEW.toString()).and(USRER_ID).is(userId)),
                Criteria.where(PERMISSIONS).elemMatch(Criteria.where(TYPE).is(PermissionType.EDIT.toString()).and(USRER_ID).is(userId))));

        LOGGER.info(query.getQueryObject().toString());
        return query;
    }


    public static Query queryFindOneUserByEmailAndPassword(String email, String password) {
        Query query = new Query();

        query.addCriteria(Criteria.where("email").is(email).and("password").is(password));

        return query;
    }
}
