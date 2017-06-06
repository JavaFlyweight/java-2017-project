package com.example.java.domain.model;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class BudgetCreateRequest extends BudgetEditRequest{

	@Getter
	@Setter
	private double balance;
        
        public BudgetCreateRequest(String name, double balance,double plannedAmount, Date dateFrom, Date dateTo){
            this.name=name;
            this.balance=balance;
            this.plannedAmount=plannedAmount;
            this.dateFrom=dateFrom;
            this.dateTo=dateTo;

        }
}