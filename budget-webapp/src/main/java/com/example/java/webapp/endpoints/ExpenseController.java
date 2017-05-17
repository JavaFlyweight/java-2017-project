package com.example.java.webapp.endpoints;

import com.example.java.application.services.ExpenseService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Expense;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
    private ExpenseService expenseService;

    @RequestMapping(value = "/getAllTypes", method = RequestMethod.GET)
    public List<ExpenseType> getAllExpenseTypes() {
        LOGGER.info("Start getAllExpenseTypes");
        return expenseService.getAllExpenseTypes();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Budget addNewExpense(@RequestParam UUID budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start addNewExpense with budgetId {}", new Object[]{budgetId});
        return expenseService.addNewExpense(budgetId, expense);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Budget deleteExpense(@RequestParam UUID budgetId, @RequestBody Expense expense) {
        LOGGER.info("Start deleteExpense with budgetId {}", new Object[]{budgetId});
        return expenseService.deleteExpense(budgetId, expense);
    }

}
