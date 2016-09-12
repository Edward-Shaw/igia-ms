package com.cloume.shaw.igia.management.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.management.iservice.IUserService;
import com.cloume.shaw.igia.management.resource.User;
import com.cloume.shaw.igia.management.rest.PagingRestResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	
	@Autowired
	private IUserService userSerivce;
	
	@RequestMapping(method = RequestMethod.GET)
	public PagingRestResponse<User> list(HttpServletRequest request, 
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", defaultValue = "default") String state,
			@RequestParam(value = "time", defaultValue = "default") String time){
		
		List<User> users = userSerivce.listByPage("true", state, time, page);
		
		return new PagingRestResponse<User>(0, "OK", users);
	}
}
