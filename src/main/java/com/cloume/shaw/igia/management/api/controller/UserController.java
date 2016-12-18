package com.cloume.shaw.igia.management.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.controller.AbstractController;
import com.cloume.shaw.igia.common.resource.User;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.iservice.IUserService;
import com.cloume.shaw.igia.management.repository.UserRepository;
import com.cloume.shaw.igia.management.rest.PagingRestResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends AbstractController{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public PagingRestResponse<User> list(HttpServletRequest request, 
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", defaultValue = "default") String state,
			@RequestParam(value = "time", defaultValue = "default") String time){
		
		List<User> users = userService.listByPage(true, state, time, page);
		
		return new PagingRestResponse<User>(0, "OK", users);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RestResponse<User> getUserDetailById(HttpServletRequest request,
			@PathVariable("id") String id){
		User user = userService.getUserById(id);
		
		return new RestResponse<User>(0, "OK", user);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.POST, RequestMethod.PUT})
	public RestResponse<User> updateUserById(HttpServletRequest request,
			@PathVariable("id") String id,
			@RequestBody Map<String, Object> body){
		User user = getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(id)), User.class);
		if(user == null){
			return RestResponse.bad(-1, "user not found!");
		}
		
		if(body.get("banned").toString().compareToIgnoreCase("true") == 0){
			body.replace("banned", true);
		}else{
			body.replace("banned", false);
		}
		
		user = new Updater<User>(user).update(body);
		user = userRepository.save(user);
		
		return new RestResponse<User>(0, "Ok", user);
	}
}
