package com.example.java.domain.model;

import com.example.java.commons.enums.ExpenseType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ExpenseReportItem {

	@Getter
	@Setter
	private ExpenseType expenseType;

	@Getter
	@Setter
	private Double amount;

}
