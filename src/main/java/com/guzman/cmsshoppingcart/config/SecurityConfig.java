package com.guzman.cmsshoppingcart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Using Spring Expression Language (SpEL)		
		// Kept for educational purposes		
//		http
//		.authorizeRequests()
//			.antMatchers("/category/**").access("hasRole('ROLE_USER')") // less access
//			.antMatchers("/").access("permitAll");// more access

		// Sequence IS important! 
		// Order should be from less access to more. 
		http
			.authorizeRequests()
				.antMatchers("/admin/**").hasAnyRole("ADMIN") // lesser access
				.antMatchers("/category/**").hasAnyRole("USER", "ADMIN") // less access
				.antMatchers("/").permitAll()// more access
					.and()
					.formLogin()
						.loginPage("/login")
					.and()
						.logout()
							.logoutSuccessUrl("/")
					.and()
						.exceptionHandling()
							.accessDeniedPage("/access-denied");
		
	}

}
