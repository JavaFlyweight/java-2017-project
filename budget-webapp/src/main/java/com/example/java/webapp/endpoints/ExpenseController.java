package com.example.java.webapp.endpoints;

import com.example.java.application.services.FinancialOperationService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
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
@RequestMapping(value = UrlPathHelper.EXPENSE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseController.class);
    
    @Autowired
    @Qualifier("expenseServiceImpl")
    private FinancialOperationService expenseService;

    @RequestMapping(value = "/getAllTypes", method = RequestMethod.GET)
    public Map<ExpenseType, String> getAllExpenseTypes() {
        LOGGER.info("Start getAllExpenseTypes");
        return expenseService.getAllFinancialOperationTypes();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Budget addNewExpense(@RequestParam UUID budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start addNewExpense with budgetId {}", new Object[]{budgetId});
        return expenseService.addNewFinancialOperation(budgetId, expense);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Budget deleteExpense(@RequestParam UUID budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start deleteExpense with budgetId {}", new Object[]{budgetId});
        return expenseService.deleteFinancialOperation(budgetId, expense);
    }

    @RequestMapping(value = "/copyWithNewDate", method = RequestMethod.POST)
    public Expense copyWithNewDate( UUID budgetId, @RequestBody Expense income, @RequestParam Date newDateTime) {
        LOGGER.info("Start copyWithNewDate with newDateTime {}", new Object[]{newDateTime});
        return (Expense) expenseService.copyWithNewDate(income, newDateTime);
    }
}
