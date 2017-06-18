package com.example.java.webapp.endpoints;

import com.example.java.application.services.FinancialOperationService;
import com.example.java.commons.enums.FinancialOperationType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
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
@RequestMapping(value = UrlPathHelper.INCOME, produces = MediaType.APPLICATION_JSON_VALUE)
public class IncomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    @Qualifier("incomeServiceImpl")
    private FinancialOperationService incomeService;

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ResponseEntity<FinancialOperationType[]> getAllIncomeTypes() {
        LOGGER.info("Start getAllIncomeTypes");
        return new ResponseEntity<>(incomeService.getAllFinancialOperationTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.POST)
    public ResponseEntity<Budget> addNewIncome(@PathVariable String budgetId, @RequestBody Income income) {
        LOGGER.info("Start addNewIncome with budgetId {}", new Object[]{budgetId});
        return new ResponseEntity<>(incomeService.addNewFinancialOperation(UUID.fromString(budgetId), income), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.DELETE)
    public ResponseEntity<Budget> deleteIncome(@PathVariable String budgetId, @RequestBody Income income) {
        LOGGER.info("Start deleteIncome with budgetId {}", new Object[]{budgetId});
        return new ResponseEntity<>(incomeService.deleteFinancialOperation(UUID.fromString(budgetId), income), HttpStatus.OK);
    }

    @RequestMapping(value = "/copy", method = RequestMethod.PUT)
    public ResponseEntity<Budget> copyIncomeWithNewDateForBudget(@RequestBody Income income, @RequestParam String newDateTime, @RequestParam UUID budgetId) {
        Date dateTime = new Date(Long.parseLong(newDateTime));
        LOGGER.info("Start copyWithNewDate with newDateTime {} for budgetId {} ", new Object[]{newDateTime, budgetId});
        return new ResponseEntity<>(incomeService.addCopiedFinancialOperationWithNewDateToBudget(income, dateTime, budgetId), HttpStatus.OK);
    }
}
