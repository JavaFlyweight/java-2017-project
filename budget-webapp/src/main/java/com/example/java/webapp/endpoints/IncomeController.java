package com.example.java.webapp.endpoints;

import com.example.java.application.services.FinancialOperationService;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.INCOME, produces = MediaType.APPLICATION_JSON_VALUE)
public class IncomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    @Qualifier("incomeServiceImpl")
    private FinancialOperationService incomeService;

    @RequestMapping(value = "/getAllTypes", method = RequestMethod.GET)
    public Map<IncomeType, String> getAllIncomeTypes() {
        LOGGER.info("Start getAllIncomeTypes");
        return incomeService.getAllFinancialOperationTypes();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Budget addNewIncome(@RequestParam UUID budgetId, @RequestBody Income income) {
        LOGGER.info("Start addNewIncome with budgetId {}", new Object[]{budgetId});
        return incomeService.addNewFinancialOperation(budgetId, income);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Budget deleteIncome(@RequestParam UUID budgetId, @RequestBody Income income) {
        LOGGER.info("Start deleteIncome with budgetId {}", new Object[]{budgetId});
        return incomeService.deleteFinancialOperation(budgetId, income);
    }
    
    @RequestMapping(value = "/copyWithNewDate", method = RequestMethod.POST)
    public Income copyWithNewDate( UUID budgetId, @RequestBody Income income, @RequestParam Date newDateTime) {
        LOGGER.info("Start copyWithNewDate with newDateTime {}", new Object[]{newDateTime});
        return (Income) incomeService.copyWithNewDate(income, newDateTime);
    }
}
