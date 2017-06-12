package com.example.java.commons.enums;

import lombok.Getter;
import lombok.Setter;

public enum IncomeType {
        SALARY(null), PREMIUM(null), PRIZE(null), GIFT(null), SHARES(null), OTHER(null);
    
        @Getter
	@Setter
        private String image;  
        
        private IncomeType(String image){
            this.image=image;
        }
}
