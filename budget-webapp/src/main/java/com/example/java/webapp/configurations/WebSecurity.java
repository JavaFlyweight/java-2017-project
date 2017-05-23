package com.example.java.webapp.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.java.commons.enums.SecurityRoles;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setUserServicePermission(http);
    }


    private void setUserServicePermission(HttpSecurity http) throws Exception {
        List<String> permission = new ArrayList<>();
        permission.add(SecurityRoles.USER.toString());
        permission.add(SecurityRoles.ADMIN.toString());
        permission.add(SecurityRoles.SERVICE.toString());

        http.csrf().disable().httpBasic().and().authorizeRequests().antMatchers("/budget/**").hasAnyRole(permission.toArray(new String[permission.size()])).and().formLogin().loginPage("/login")
                .permitAll().and().logout().permitAll();
    }
}
