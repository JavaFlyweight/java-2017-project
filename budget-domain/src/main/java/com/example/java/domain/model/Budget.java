package com.example.java.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

public class Budget {

	@Id
	@Getter
	private UUID id;

	@Getter
	@Setter
	private Set<Permission> permissions = new HashSet<>();

	@Getter
	@Setter
	private Set<Expense> expenses = new HashSet<>();

	@Getter
	@Setter
	private Set<Income> incomes = new HashSet<>();

	@Getter
	@Setter
	private double balance;

	@Getter
	@Setter
	private double plannedAmount;

	@Getter
	@Setter
	private Date dateFrom;

	@Getter
	@Setter
	private Date dateTo;
}