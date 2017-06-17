package com.example.java.domain.model;

import com.example.java.commons.enums.IncomeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class IncomeReportItem {

	@Getter
	@Setter
	private IncomeType incomeType;

	@Getter
	@Setter
	private Double amount;

}
