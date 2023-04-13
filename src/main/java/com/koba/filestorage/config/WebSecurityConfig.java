package com.koba.filestorage.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.antMatcher("/api/v1/**");

        //http.authorizeRequests().anyRequest().authenticated();

        http.httpBasic().realmName("your secret area");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


}