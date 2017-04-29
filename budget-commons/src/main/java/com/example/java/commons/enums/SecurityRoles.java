package com.example.java.commons.enums;

public enum SecurityRoles {
	ADMIN, USER, SERVICE;
	
	private final static String ROLE_PREFIX = "ROLE_";
	
	public String getAsRoleName() {
		return ROLE_PREFIX + this.toString();
	}
}
