package com.example.java.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Budget")
public class Budget {

    @Id
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;
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

    @Getter
    @Setter
    private String image;

    public Budget() {
        this.id = UUID.randomUUID();
    }

    public Budget(String name, double balance, double plannedAmount, Date dateFrom, Date dateTo, String image) {
        this.name = name;
        this.balance = balance;
        this.plannedAmount = plannedAmount;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.id = UUID.randomUUID();
        this.image = image;
    }

    public Budget(Budget budgetToClone) {
        super();
        this.id = UUID.randomUUID();
        this.name = budgetToClone.name;
        this.balance = budgetToClone.balance;
        this.plannedAmount = budgetToClone.plannedAmount;
        this.dateFrom = budgetToClone.dateFrom;
        this.dateTo = budgetToClone.dateTo;
        this.image = budgetToClone.image;
        this.permissions = new HashSet<>();
        this.expenses = new HashSet<>(budgetToClone.expenses);
        this.incomes = new HashSet<>(budgetToClone.incomes);
    }
}
