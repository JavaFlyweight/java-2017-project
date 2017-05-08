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
        
        public Budget(){
            this.id = UUID.randomUUID();
        }
        
        public Budget(double balance,double plannedAmount, Date dateFrom, Date dateTo){
            this.balance=balance;
            this.plannedAmount=plannedAmount;
            this.dateFrom=dateFrom;
            this.dateTo=dateTo;
            this.id = UUID.randomUUID();
        }
}