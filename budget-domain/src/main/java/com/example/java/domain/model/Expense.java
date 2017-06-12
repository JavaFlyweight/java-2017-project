package com.example.java.domain.model;

import java.util.Date;
import java.util.UUID;

import com.example.java.commons.enums.ExpenseType;

import lombok.Getter;

public class Expense extends FinancialOperation {

    @Getter
    private ExpenseType expenseType;

    public Expense(){};
        
    public Expense(String name, double amount, UUID addedBy, ExpenseType expenseType, Date dateTime) {
        this.name = name;
        this.amount = amount;
        this.addedBy = addedBy;
        this.expenseType = expenseType;
        this.dateTime = dateTime;
    }

    public Expense(Expense expenseToClone) {
        super();
        this.name = expenseToClone.name;
        this.amount = expenseToClone.amount;
        this.addedBy = expenseToClone.addedBy;
        this.expenseType = expenseToClone.expenseType;
        this.dateTime = expenseToClone.dateTime;
    }
}
