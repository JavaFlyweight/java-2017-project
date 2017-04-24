package pl.lodz.p.java.flyweight.utils;

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

    public static Query queryFindOneByUserIdAndOwner(long userId) {
        Query query = new Query();

        query.addCriteria(Criteria.where(QueryUtils.PERMISSIONS).elemMatch(
                Criteria.where(QueryUtils.USRER_ID).is(userId).and(
                QueryUtils.TYPE).is(PermissionType.OWNER.toString())));
        LOGGER.info(query.getQueryObject().toString());
        return query;
    }

    public static Query queryFindOneByUserIdAndShared(long userId) {
        Query query = new Query();

        query.addCriteria(new Criteria().orOperator(
                Criteria.where(QueryUtils.PERMISSIONS).elemMatch(Criteria.where(QueryUtils.TYPE).is(PermissionType.VIEW.toString()).and(QueryUtils.USRER_ID).is(
                userId)),
                Criteria.where(QueryUtils.PERMISSIONS).elemMatch(Criteria.where(QueryUtils.TYPE).is(PermissionType.EDIT.toString()).and(QueryUtils.USRER_ID).is(
                userId))));
        LOGGER.info(query.getQueryObject().toString());
        return query;
    }
}
