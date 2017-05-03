package com.example.java.webapp.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.BudgetService;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.repository.queries.BudgetQuery;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.BUDGET, produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @RequestMapping(value = "/getToEdit", method = RequestMethod.GET)
    public Budget getOneBudgetToEdit(UUID budgetId) {
        LOGGER.info("Start getOneBudget with budgetId {}", new Object[]{budgetId});
        return budgetService.getOneBudgetEntityToEdit(budgetId);
    }

    @RequestMapping(value = "/getToView", method = RequestMethod.GET)
    public Budget getOneBudgetToView(UUID budgetId) {
        LOGGER.info("Start getOneBudget with budgetId {}", new Object[]{budgetId});
        return budgetService.getOneBudgetEntityToEdit(budgetId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Budget createBudgetEntity(@RequestBody Budget dataToCreateBudget) {
        LOGGER.info("Start createBudgetEntity {} {} {} {}", new Object[]{dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo()});
        return budgetService.createBudgetEntity(dataToCreateBudget);
    }

//    @RequestMapping(value = "/getAllMe", method = RequestMethod.GET)
//    public List<Budget> getAllByUserIdAndOwner() {
//        LOGGER.debug("Start getAllByUserIdAndOwner ");
//        return budgetService.getAllByUserIdAndOwner();
//    }
//
//    @RequestMapping(value = "/getShared", method = RequestMethod.GET)
//    public List<Budget> getSharedBudgets() {
//        LOGGER.debug("Start getSharedBudgets ");
//        return budgetService.getSharedBudgets();
//    }
}
