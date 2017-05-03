package com.example.java.application.services;

import java.util.Arrays;
import java.util.Set;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Permission;

public class UtilsService {

	public static boolean checkPermissionForBudget(Budget budgetToCheck, String userName,
			PermissionType... permissionTypes) {
		Set<Permission> permissions = budgetToCheck.getPermissions();

		PermissionType userPermission = permissions.stream().filter(p -> p.getUserName().equals(userName)).findAny()
				.get().getPermissionType();

		if (Arrays.asList(permissionTypes).contains(userPermission)) {
			return true;
		} else {
			return false;
		}
	}
}
