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

@Controller
public class DefaultController {

    private static final String USER_NAME = "userName";

    private static final String REDIRECT = "redirect:";


    @RequestMapping(value = UrlPathHelper.LOGOUT, method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return REDIRECT + UrlPathHelper.INDEX;
    }


    @RequestMapping(value = UrlPathHelper.INDEX, method = RequestMethod.GET)
    public ModelAndView indexPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("index");

        if (request.getRemoteUser() != null) {
            modelAndView.addObject(USER_NAME, request.getRemoteUser());
        }

        return modelAndView;
    }


    @RequestMapping(value = UrlPathHelper.LOGIN, method = RequestMethod.GET)
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("login");

        return modelAndView;
    }
}