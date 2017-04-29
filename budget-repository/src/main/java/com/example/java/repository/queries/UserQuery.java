package com.example.java.repository.queries;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class UserQuery {
	private static final String EMAIL = "email";

	public static Query queryFindOneByEmail(String email) {
		Query query = new Query();

		query.addCriteria(Criteria.where(EMAIL).is(email));

		return query;
	}
}
