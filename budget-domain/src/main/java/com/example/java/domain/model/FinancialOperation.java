package com.example.java.domain.model;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public abstract class FinancialOperation {
	
	@Getter
	@Setter
	protected String name;
	
	@Getter
	@Setter
	protected double amount;
	
	@Getter
	@Setter
	protected Date dateTime;
	
	@Getter
	@Setter
	protected UUID addedBy;
        
        @Getter
	@Setter
	protected String addedByUserEmail;
}
