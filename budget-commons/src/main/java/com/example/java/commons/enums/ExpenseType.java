package com.example.java.commons.enums;

import lombok.Getter;
import lombok.Setter;

public enum ExpenseType {
	HOUSEHOLD(null), FOOD(null), STUDY(null), HEALTHCARE(null), CHILDREN(null), SHOPPING(null), ENTERTAINMENT(null), VEHICLE(null), OTHER(null);
        
        @Getter
	@Setter
        private String image;  
        
        private ExpenseType(String image){
            this.image=image;
        }
}
