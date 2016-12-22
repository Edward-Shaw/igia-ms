package com.cloume.shaw.igia.management.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.common.resource.Subscribe;
import com.cloume.shaw.igia.common.resource.User;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.management.iservice.ICourseService;
import com.cloume.shaw.igia.management.iservice.ISubscribeService;
import com.cloume.shaw.igia.management.iservice.IUserService;

@Controller
public class MainController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISubscribeService subscribeService;
	
	@Autowired
	private ICourseService courseService;
	
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
			@RequestParam(value = "banned", required = false, defaultValue = "true") boolean banned,
			@RequestParam(value = "time", required = false, defaultValue = "default") String time,
			Model model){
		
		List<User> users = userService.listByPage(banned, state, time, page);
		
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
	
	@RequestMapping(value = "/course", method = {RequestMethod.GET})
	public String coursePage(Principal principal,
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", required = false, defaultValue = "default") String state,
			@RequestParam(value = "time", required = false, defaultValue = "default") String createdTime,
			@RequestParam(value = "classification", required = false, defaultValue = "default") String classification,
			Model model){
		
		switch(classification){
		case "0":
			classification = Const.COURSE_PAINTING;
			break;
		case "1":
			classification = Const.COURSE_DANCE;
			break;
		case "2":
			classification = Const.COURSE_TAEKWONDO;
			break;
		case "3":
			classification = Const.COURSE_YOGA;
			break;
		case "4":
			classification = Const.COURSE_SCIENCE;
			break;
		case "5":
			classification = Const.COURSE_CAMP;
			break;
		default:
			classification = "default";
		}
		
		switch(state){
		case "unpublished":
			state = Const.STATE_UNPUBLISHED;
			break;
		case "published":
			state = Const.STATE_PUBLISHED;
			break;
		case "deleted":
			state = Const.STATE_DELETED;
			break;
		default:
			state = "default";
		}
		
		List<Course> courses = courseService.listByPage(state, classification, createdTime, page);
		
		model.addAttribute("courses", courses);
		
		return "course";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 作业管理
	 * @return
	 */
	@RequestMapping("/task")
	public String task(){
		return "task";
	}
}
