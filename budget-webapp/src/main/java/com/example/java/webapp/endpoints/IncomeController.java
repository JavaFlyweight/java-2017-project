package com.example.java.webapp.endpoints;

import com.example.java.application.services.IncomeService;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.Income;
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
@RequestMapping(value = UrlPathHelper.INCOME, produces = MediaType.APPLICATION_JSON_VALUE)
public class IncomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeController.class);

    @Autowired
    private IncomeService incomeService;

    @RequestMapping(value = "/getAllTypes", method = RequestMethod.GET)
    public List<IncomeType> getAllIncomeTypes() {
        LOGGER.info("Start getAllIncomeTypes");
        return incomeService.getAllIncomeTypes();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Budget addNewIncome(@RequestParam UUID budgetId, @RequestBody Income income) {
        LOGGER.info("Start addNewIncome with budgetId {}", new Object[]{budgetId});
        return incomeService.addNewIncome(budgetId, income);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Budget deleteIncome(@RequestParam UUID budgetId, @RequestBody Income income) {
        LOGGER.info("Start deleteIncome with budgetId {}", new Object[]{budgetId});
        return incomeService.deleteIncome(budgetId, income);
    }

}
