package com.cloume.shaw.igia.management.api.controller;

import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.controller.AbstractController;
import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.common.resource.User;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.iservice.ICourseService;

@RestController
@RequestMapping(value = "/api/course")
public class CourseController extends AbstractController{
	
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
		course.setCode(generateCourseCode());
		
		courseService.addNewCourse(course);
		
		return RestResponse.good(course);
	}
	
	/**
	 * code = YYMM(年月)-用户数量序号(位数根据数据库中实际用户数量进行动态变更)
	 * 
	 * @return
	 */
	private String generateCourseCode() {
		Calendar now = Calendar.getInstance();
		String time = (now.get(Calendar.YEAR) + "").substring(2) + String.format("%02d", now.get(Calendar.MONTH) + 1);
		final String prefix = "C-" + time;
		final String pattern = prefix + "-\\d{1,5}";

		String code = "";
		synchronized (this) {
			long count = getMongoTemplate().count(
					Query.query(Criteria.where("code").regex(pattern).and("state").ne(Const.STATE_DELETED)), Course.class,
					"course") + 1;
			code = prefix + "-" + count;
		}

		return code;
	}
}
