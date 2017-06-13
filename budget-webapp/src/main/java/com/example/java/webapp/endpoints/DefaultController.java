package com.example.java.webapp.endpoints;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.java.commons.enums.ViewTemplatesResolver;
import com.example.java.commons.http.UrlPathHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class DefaultController {
	
	private static final String USER_NAME = "userName";
	
	private static final String REDIRECT = "redirect:";

	@RequestMapping(value = UrlPathHelper.LOGOUT, method = RequestMethod.GET)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return new ResponseEntity<>(REDIRECT + UrlPathHelper.INDEX, HttpStatus.OK);
	}

	@RequestMapping(value = UrlPathHelper.INDEX, method = RequestMethod.GET)
	public ResponseEntity<ModelAndView> indexPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(ViewTemplatesResolver.INDEX.toString());	
		
		if (request.getRemoteUser() != null) {
			modelAndView.addObject(USER_NAME, request.getRemoteUser());			
		}
				
		return new ResponseEntity<>(modelAndView, HttpStatus.OK);
	}
	
	@RequestMapping(value = UrlPathHelper.LOGIN, method = RequestMethod.GET)
	public ResponseEntity<ModelAndView> loginPage() {
		ModelAndView modelAndView = new ModelAndView(ViewTemplatesResolver.LOGIN.toString());
		
		return new ResponseEntity<>(modelAndView, HttpStatus.OK);
	}
}