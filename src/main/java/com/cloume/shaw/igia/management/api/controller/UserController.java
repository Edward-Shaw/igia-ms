package com.cloume.shaw.igia.management.api.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.management.resource.IgiaUser;
import com.cloume.shaw.igia.management.rest.PagingRestResponse;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	public PagingRestResponse<IgiaUser> list(){
		
		ArrayList<IgiaUser> users = new ArrayList<IgiaUser>();
		
		//TODO:add service impl.
		
		return new PagingRestResponse<IgiaUser>(0, "OK", users);
	}
}
