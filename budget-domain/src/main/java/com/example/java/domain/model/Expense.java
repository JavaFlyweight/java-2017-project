package com.example.java.domain.model;

import java.util.UUID;

import com.example.java.commons.enums.OperationType;

import lombok.Getter;

public class Expense extends Operation {

	@Getter
	private OperationType operationType;

	public Expense(String name, double amount, UUID addedBy) {
		this.name = name;
		this.amount = amount;
		this.addedBy = addedBy;
		this.operationType = OperationType.EXPENSE;
	}
}
