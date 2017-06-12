package com.example.java.domain.model;

import java.util.Date;
import java.util.UUID;

import com.example.java.commons.enums.IncomeType;

import lombok.Getter;

public class Income extends FinancialOperation {

    @Getter
    private IncomeType incomeType;

    public Income() {};

    public Income(String name, double amount, UUID addedBy, IncomeType incomeType, Date dateTime) {
        this.name = name;
        this.amount = amount;
        this.addedBy = addedBy;
        this.incomeType = incomeType;
        this.dateTime = dateTime;
    }

    public Income(Income incomeToClone) {
        super();
        this.name = incomeToClone.name;
        this.amount = incomeToClone.amount;
        this.addedBy = incomeToClone.addedBy;
        this.incomeType = incomeToClone.incomeType;
        this.dateTime = incomeToClone.dateTime;
    }
}
