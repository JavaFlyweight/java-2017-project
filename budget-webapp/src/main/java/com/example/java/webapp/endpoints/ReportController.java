package com.example.java.webapp.endpoints;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.ReportService;
import com.example.java.commons.enums.ExpenseType;
import com.example.java.commons.enums.IncomeType;
import com.example.java.commons.http.UrlPathHelper;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.REPORT, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/getExpensesToView", method = RequestMethod.GET)
	public Map<ExpenseType, Double> getSumsExpensesPerType(@RequestParam UUID budgetId, @RequestParam Date dateFrom,
			@RequestParam Date dateTo) {
		LOGGER.debug("Start getSumsExpensesPerType with budgetId {0}", new Object[] { budgetId });
		return reportService.getAllSumsExpensesPerType(budgetId, dateFrom, dateTo);
	}

	@RequestMapping(value = "/getIncomesToView", method = RequestMethod.GET)
	public Map<IncomeType, Double> getSumsIncomesPerType(@RequestParam UUID budgetId, @RequestParam Date dateFrom,
			@RequestParam Date dateTo) {
		LOGGER.debug("Start getSumsIncomesPerType with budgetId {0}", new Object[] { budgetId });
		return reportService.getAllSumsIncomesPerType(budgetId, dateFrom, dateTo);
	}
}
