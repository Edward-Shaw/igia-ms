package com.cloume.shaw.igia.management.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.iservice.ICourseService;

@RestController
@RequestMapping(value = "/api/course")
public class CourseController {
	
	@Autowired
	private ICourseService courseService;
	
	/**
	 * 增加新的课程
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "" ,method = {RequestMethod.POST , RequestMethod.PUT})
	public RestResponse<Course> addNewCourse(@RequestBody Map<String, Object> body){
		
		Course course = new Updater<Course>(new Course()).update(body);
		
		course.setCreatedTime(System.currentTimeMillis());
		
		courseService.addNewCourse(course);
		
		return RestResponse.good(course);
	}
}
