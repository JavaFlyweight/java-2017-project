package com.example.java.repository.queries;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.java.commons.enums.PermissionType;

public class BudgetQuery {

    public static final String PERMISSIONS = "permissions";
    public static final String USER_LOGIN = "userLogin";
    public static final String TYPE = "permissionType";

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetQuery.class);

    public static Query queryFindAllByUserLoginAndPermissions(String userLogin, PermissionType permission) {
        Query query = new Query();

        query.addCriteria(Criteria.where(PERMISSIONS)
                .elemMatch(Criteria.where(USER_LOGIN).is(userLogin).and(TYPE).is(permission.toString())));

        LOGGER.info(query.getQueryObject().toString());
        return query;

    }
}
