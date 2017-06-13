package com.example.java.webapp.endpoints;

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

import com.example.java.application.services.BudgetService;
import com.example.java.commons.enums.PermissionType;
import com.example.java.commons.http.UrlPathHelper;
import com.example.java.domain.model.Budget;
import com.example.java.domain.model.BudgetCreateRequest;
import com.example.java.domain.model.BudgetEditRequest;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.BUDGET, produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Budget getOneById(@RequestParam UUID budgetId) {
        LOGGER.info("Start getOneBudget with budgetId {}", new Object[]{budgetId});
        return budgetService.getOneById(budgetId, PermissionType.OWNER, PermissionType.EDIT, PermissionType.VIEW);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Budget createBudgetEntity(@RequestBody BudgetCreateRequest dataToCreateBudget) {
        LOGGER.info("Start createBudgetEntity {} {} {} {}",
                new Object[]{dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo()});

        return budgetService.createBudgetEntity(dataToCreateBudget);
    }

    @RequestMapping(value = "/getMy", method = RequestMethod.GET)
    public List<Budget> getByUserLoginAndOwner() {
        LOGGER.info("Start getAllByUserIdAndOwner ");
        return budgetService.getAllByUserLoginAndOwner();
    }

    @RequestMapping(value = "/getShared", method = RequestMethod.GET)
    public List<Budget> getSharedBudgets() {
        LOGGER.info("Start getSharedBudgets ");
        return budgetService.getSharedBudgets();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Budget editBudgetEntity(@RequestParam UUID budgetId, @RequestBody BudgetEditRequest dataToEditBudget) {
        LOGGER.info("Start editBudgetEntity {} {} {} {} ", new Object[]{budgetId, dataToEditBudget.getPlannedAmount(), dataToEditBudget.getDateFrom(), dataToEditBudget.getDateTo()});
        return budgetService.editBudgetEntity(budgetId, dataToEditBudget);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetEntity(@RequestParam UUID budgetId) {
        LOGGER.info("Start deleteBudgetEntity with budgetId {} ", new Object[]{budgetId});
        budgetService.deleteBudgetEntity(budgetId);
    }

    @RequestMapping(value = "/share", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Budget shareBudgetEntity(@RequestParam UUID budgetId, @RequestParam String userLogin, @RequestParam PermissionType permissionType) {
        LOGGER.info("Start shareBudgetEntity with budgetId {}, userLogin {} and permission to share {} ", new Object[]{budgetId, userLogin, permissionType});
        return budgetService.shareBudget(budgetId, userLogin, permissionType);
    }

    @RequestMapping(value = "/unshare", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Budget unshareBudgetEntity(@RequestParam UUID budgetId, @RequestParam String userLogin) {
        LOGGER.info("Start unshareBudgetEntity with budgetId {} and userLogin {} ", new Object[]{budgetId, userLogin});
        return budgetService.unshareBudget(budgetId, userLogin);
    }
}
