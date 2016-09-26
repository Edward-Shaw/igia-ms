package com.cloume.shaw.igia.management.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloume.shaw.igia.management.iservice.ISubscribeService;
import com.cloume.shaw.igia.management.iservice.IUserService;
import com.cloume.shaw.igia.management.resource.Subscribe;
import com.cloume.shaw.igia.management.resource.User;

@Controller
public class MainController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISubscribeService subscribeService;
	
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
	public String userPage(Principal principal,
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", required = false, defaultValue = "default") String state,
			@RequestParam(value = "time", required = false, defaultValue = "default") String time,
			Model model){
		
		List<User> users = userService.listByPage("true", state, time, page);
		
		model.addAttribute("users", users);
		
		return "user";
	}
	
	@RequestMapping(value = "/subscribe", method = {RequestMethod.GET})
	public String subscribePage(Principal principal,
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", required = false, defaultValue = "default") String state,
			@RequestParam(value = "time", required = false, defaultValue = "default") String createTime,
			@RequestParam(value = "subscribe_time", required = false, defaultValue = "default") String subscribeTime,
			Model model){
		
		List<Subscribe> subscribes = subscribeService.listByPage(state, createTime, subscribeTime, page);
		
		model.addAttribute("subscribes", subscribes);
		
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
