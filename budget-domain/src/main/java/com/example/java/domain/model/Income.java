package com.example.java.domain.model;

import java.util.UUID;

import com.example.java.commons.enums.IncomeType;

import lombok.Getter;

public class Income extends Operation {

	@Getter
	private IncomeType incomeType;

	public Income(String name, double amount, UUID addedBy, IncomeType incomeType) {
		this.name = name;
		this.amount = amount;
		this.addedBy = addedBy;
		this.incomeType = incomeType;
	}
}
