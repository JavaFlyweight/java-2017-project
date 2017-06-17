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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.BUDGET, produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.GET)
    public ResponseEntity<Budget> getOneById(@PathVariable String budgetId) {
        LOGGER.info("Start getOneBudget with budgetId {}", new Object[]{budgetId});
        return new ResponseEntity<>(budgetService.getOneById(UUID.fromString(budgetId), PermissionType.OWNER, PermissionType.EDIT, PermissionType.VIEW), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> createBudgetEntity(@RequestBody BudgetCreateRequest dataToCreateBudget) {
        LOGGER.info("Start createBudgetEntity {} {} {} {}",
                new Object[]{dataToCreateBudget.getBalance(), dataToCreateBudget.getPlannedAmount(), dataToCreateBudget.getDateFrom(), dataToCreateBudget.getDateTo()});
        return new ResponseEntity<>(budgetService.createBudgetEntity(dataToCreateBudget), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ResponseEntity<List<Budget>> getByUserLoginAndOwner() {
        LOGGER.info("Start getAllByUserIdAndOwner ");
        return new ResponseEntity<>(budgetService.getAllByUserLoginAndOwner(), HttpStatus.OK);
    }

    @RequestMapping(value = "/shared", method = RequestMethod.GET)
    public ResponseEntity<List<Budget>> getSharedBudgets() {
        LOGGER.info("Start getSharedBudgets ");
        return new ResponseEntity<>(budgetService.getSharedBudgets(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> editBudgetEntity(@PathVariable String budgetId, @RequestBody BudgetEditRequest dataToEditBudget) {
        LOGGER.info("Start editBudgetEntity {} {} {} {} ", new Object[]{budgetId, dataToEditBudget.getPlannedAmount(), dataToEditBudget.getDateFrom(), dataToEditBudget.getDateTo()});
        return new ResponseEntity<>(budgetService.editBudgetEntity(UUID.fromString(budgetId), dataToEditBudget), HttpStatus.OK);
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteBudgetEntity(@PathVariable String budgetId) {
        LOGGER.info("Start deleteBudgetEntity with budgetId {} ", new Object[]{budgetId});
        budgetService.deleteBudgetEntity(UUID.fromString(budgetId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/share", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> shareBudgetEntity(@RequestParam UUID budgetId, @RequestParam String userLogin, @RequestParam PermissionType permissionType) {
        LOGGER.info("Start shareBudgetEntity with budgetId {}, userLogin {} and permission to share {} ", new Object[]{budgetId, userLogin, permissionType});
        return new ResponseEntity<>(budgetService.shareBudget(budgetId, userLogin, permissionType), HttpStatus.OK);
    }

    @RequestMapping(value = "/unshare", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> unshareBudgetEntity(@RequestParam UUID budgetId, @RequestParam String userLogin) {
        LOGGER.info("Start unshareBudgetEntity with budgetId {} and userLogin {} ", new Object[]{budgetId, userLogin});
        return new ResponseEntity<>(budgetService.unshareBudget(budgetId, userLogin), HttpStatus.OK);
    }

    @RequestMapping(value = "/copy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> copyBudgetEntity(@RequestParam UUID budgetId) {
        LOGGER.info("Start copyBudgetEntity with budgetId {} ", new Object[]{budgetId});
        return new ResponseEntity<>(budgetService.copyBudget(budgetId), HttpStatus.CREATED);
    }
}
