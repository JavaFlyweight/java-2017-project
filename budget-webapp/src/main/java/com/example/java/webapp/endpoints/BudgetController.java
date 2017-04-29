package com.example.java.webapp.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.BudgetService;
import com.example.java.commons.http.UrlPathHelper;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.BUDGET, produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

	@Autowired
    private BudgetService budgetService;
}
