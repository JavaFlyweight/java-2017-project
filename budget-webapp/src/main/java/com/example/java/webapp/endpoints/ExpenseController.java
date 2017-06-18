package com.example.java.webapp.endpoints;

import com.example.java.application.services.FinancialOperationService;
import com.example.java.commons.enums.FinancialOperationType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.EXPENSE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    @Qualifier("expenseServiceImpl")
    private FinancialOperationService expenseService;

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ResponseEntity<FinancialOperationType[]> getAllExpenseTypes() {
        LOGGER.info("Start getAllExpenseTypes");
        return new ResponseEntity<>(expenseService.getAllFinancialOperationTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.POST)
    public ResponseEntity<Budget> addNewExpense(@PathVariable String budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start addNewExpense with budgetId {}", new Object[]{budgetId});
        return new ResponseEntity<>(expenseService.addNewFinancialOperation(UUID.fromString(budgetId), expense), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.DELETE)
    public ResponseEntity<Budget> deleteExpense(@PathVariable String budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start deleteExpense with budgetId {}", new Object[]{budgetId});
        return new ResponseEntity<>(expenseService.deleteFinancialOperation(UUID.fromString(budgetId), expense), HttpStatus.OK);
    }

    @RequestMapping(value = "/copy", method = RequestMethod.PUT)
    public ResponseEntity<Budget> copyExpenseWithNewDateForBudget(@RequestBody Expense income, @RequestParam String newDateTime, @RequestParam UUID budgetId) {
        Date dateTime = new Date(Long.parseLong(newDateTime));
        LOGGER.info("Start copyWithNewDate with newDateTime {} for budgetId {} ", new Object[]{newDateTime, budgetId });
        return new ResponseEntity<>(expenseService.addCopiedFinancialOperationWithNewDateToBudget(income, dateTime, budgetId), HttpStatus.OK);
    }
}
