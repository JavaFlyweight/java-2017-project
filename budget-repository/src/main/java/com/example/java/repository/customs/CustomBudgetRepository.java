package com.example.java.repository.customs;

import java.util.List;
import java.util.UUID;

import com.example.java.commons.enums.PermissionType;
import com.example.java.domain.model.Budget;

public interface CustomBudgetRepository {

	public Budget findOneById(UUID budgetId);

	public List<Budget> findAllByIdAndPermissions(UUID userId, PermissionType permission);		
}
