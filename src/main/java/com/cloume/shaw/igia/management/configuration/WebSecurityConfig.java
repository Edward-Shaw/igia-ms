package com.cloume.shaw.igia.management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableWebSecurity
/*
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .antMatchers("/js/**", "/less/**", "/images/**", "/css/**", "/fonts/**", "/api/**", "/wares/**", "/racks/**", "/storagein/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	        	.permitAll()
	        	.defaultSuccessUrl("/index", true)
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
*/