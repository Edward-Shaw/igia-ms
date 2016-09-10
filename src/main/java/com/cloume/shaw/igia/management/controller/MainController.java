package com.cloume.shaw.igia.management.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	public String index(Principal principal){
		
		/*
		if(principal == null || principal.getName() == null){
			return "error";
		}
		*/
		
		return "index";
	}
}
