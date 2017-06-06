package com.example.java.domain.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BudgetEditRequest {
       
        @Getter
	@Setter
        protected String name;

	@Getter
	@Setter
	protected double plannedAmount;

	@Getter
	@Setter
	protected Date dateFrom;

	@Getter
	@Setter
	protected Date dateTo;
}