package com.guzman.cmsshoppingcart.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Most usual way of configure.
		// Sequence IS important! 
		// Order should be from less access to more. 
		http
			.authorizeRequests()
				.antMatchers("/category/**").hasAnyRole("USER") // less access
				.antMatchers("/").permitAll();// more access
		
		// Using Spring Expression Language (SpEL)		
//		http
//		.authorizeRequests()
//			.antMatchers("/category/**").access("hasRole('ROLE_USER')") // less access
//			.antMatchers("/").access("permitAll");// more access
	}

}
