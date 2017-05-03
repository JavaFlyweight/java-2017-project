package com.example.java.webapp.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.application.services.ReportService;
import com.example.java.commons.http.UrlPathHelper;

@RestController
@Controller
@RequestMapping(value = UrlPathHelper.REPORT, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;
}
