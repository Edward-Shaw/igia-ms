package com.cloume.shaw.igia.management.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	public String indexPage(Principal principal){
		
		/*
		if(principal == null || principal.getName() == null){
			return "error";
		}
		*/
		
		return "index";
	}
	
	@RequestMapping(value = "/user", method = {RequestMethod.GET})
	public String userPage(Principal principal){
		
		/*
		if(principal == null || principal.getName() == null){
			return "error";
		}
		*/
		
		return "user";
	}
	
	@RequestMapping(value = "/subscribe", method = {RequestMethod.GET})
	public String subscribePage(Principal principal){
		
		/*
		if(principal == null || principal.getName() == null){
			return "error";
		}
		*/
		
		return "subscribe";
	}
	
	@RequestMapping(value = "/class", method = {RequestMethod.GET})
	public String classPage(Principal principal){
		
		/*
		if(principal == null || principal.getName() == null){
			return "error";
		}
		*/
		
		return "class";
	}
}
