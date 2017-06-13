package com.example.java.commons.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum IncomeType implements FinancialOperationType {
    SALARY(), PREMIUM(), PRIZE(), GIFT(), SHARES(), OTHER();

    @Getter
    @Setter
    private String image;

    @Getter
    private String name;

    private final ResourceBundle rb = ResourceBundle.getBundle("application");
    private final Logger LOGGER = LoggerFactory.getLogger(IncomeType.class);

    private IncomeType() {
        try {
            this.image = rb.getString("income.url.type." + this.name().toLowerCase());
            this.name = this.name();
        } catch (MissingResourceException e) {
            LOGGER.warn("Image url not found for " + this.name().toLowerCase());
        }
    }
}
