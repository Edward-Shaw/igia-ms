package com.cloume.shaw.igia.management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .antMatchers("/js/**", "/less/**", "/images/**", "/css/**", "/fonts/**", "/api/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	        	.loginPage("/login").permitAll()
	        	.defaultSuccessUrl("/user", true)
	        	.and()
	        .csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//保留此账户用于测试
		auth
	        .inMemoryAuthentication()
	        	.withUser("admin").password("pl,okm123").roles("ADMIN")
	        	.and().withUser("user").password("password").roles("USER");
	}
}