package pl.lodz.p.java.flyweight.controller;

import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.java.flyweight.service.BudgetService;

@RestController
@RequestMapping(value = "/budget", produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

    private final BudgetService budgetService;

    @Inject
    public BudgetController(final BudgetService budgetService) {
        this.budgetService = budgetService;
    }
}
