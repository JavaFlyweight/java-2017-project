package com.example.java.commons.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExpenseType {
	HOUSEHOLD(), FOOD(), STUDY(), HEALTHCARE(), CHILDREN(), SHOPPING(), ENTERTAINMENT(), VEHICLE(), OTHER();
        
    @Getter
    @Setter
    private String image;  

    @Getter
    private String name;
        
    private final ResourceBundle rb = ResourceBundle.getBundle("application");
    private final Logger LOGGER = LoggerFactory.getLogger(ExpenseType.class);

    private ExpenseType() {
        try {
            this.image = rb.getString("expense.url.type." + this.name().toLowerCase());
            this.name = this.name();
        } catch (MissingResourceException e) {
             LOGGER.warn("Image url not found for "+this.name().toLowerCase());
        }
    }
}
