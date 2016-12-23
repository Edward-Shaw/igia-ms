package com.cloume.shaw.igia.management.api.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.iservice.ICourseService;
import com.cloume.shaw.igia.management.repository.CourseRepository;

@RestController
@RequestMapping(value = "/api/course")
public class CourseController extends AbstractController{
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	final String[] FIELDS = new String[] {"name", "content", "classification", "state"};
	
	/**
	 * 增加新的课程
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "" ,method = {RequestMethod.POST , RequestMethod.PUT})
	public RestResponse<Course> addNewCourse(@RequestBody Map<String, Object> body){
		
		for (String field : FIELDS) {
			if (!body.containsKey(field)) {
				return RestResponse.bad(-10001, String.format("missed property %s", field), null);
			}
		}
		
		Course course = new Updater<Course>(new Course()).update(body);
		
		switch(course.getClassification()){
		case "0":
			course.setClassification(Const.COURSE_PAINTING);
			break;
		case "1":
			course.setClassification(Const.COURSE_DANCE);
			break;
		case "2":
			course.setClassification(Const.COURSE_TAEKWONDO);
			break;
		case "3":
			course.setClassification(Const.COURSE_YOGA);
			break;
		case "4":
			course.setClassification(Const.COURSE_SCIENCE);
			break;
		case "5":
			course.setClassification(Const.COURSE_CAMP);
			break;
		default:
			course.setClassification(Const.COURSE_UNKNOWN);
		}
		
		switch(course.getState()){
		case "unpublished":
			course.setState(Const.STATE_UNPUBLISHED);
			break;
		case "published":
			course.setState(Const.STATE_PUBLISHED);
			break;
		default:
			course.setState(Const.STATE_UNKNOWN);
		}
		
		course.setCreatedTime(System.currentTimeMillis());
		course.setCode(generateCourseCode());
		
		courseService.addNewCourse(course);
		
		return RestResponse.good(course);
	}
	
	/**
	 * 根据指定id删除课程：将课程状态置为已删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public RestResponse<Course> deleteCourseById(@PathVariable("id") String id){
		
		Course course = courseService.deleteCourseById(id);
		
		return new RestResponse<Course>(0, "OK", course);
	}
	
	/**
	 * 修改课程内容，包括发布/撤回课程
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public RestResponse<Course> updateCourseState(@PathVariable("id") String id,
			@RequestBody Map<String, Object> body){
		
		Course course = getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(id)), Course.class);
		if(course == null){
			return RestResponse.bad(-1, "course not found!");
		}

		course = new Updater<Course>(course).update(body, (key, value)->{
			if(key.toString().compareToIgnoreCase("classification") == 0){
				switch(value.toString()){
				case "0":
					value = Const.COURSE_PAINTING;
					break;
				case "1":
					value = Const.COURSE_DANCE;
					break;
				case "2":
					value = Const.COURSE_TAEKWONDO;
					break;
				case "3":
					value = Const.COURSE_YOGA;
					break;
				case "4":
					value = Const.COURSE_SCIENCE;
					break;
				case "5":
					value = Const.COURSE_CAMP;
					break;
				default:
					value = Const.COURSE_UNKNOWN;
				}
			}
			return value;
		});
		course = courseRepository.save(course);
		
		return new RestResponse<Course>(0, "OK", course);
	}
	
	/**
	 * 根据指定@id获取课程详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RestResponse<Course> getCourseById(@PathVariable("id") String id){
		Course course = courseService.findCourseById(id);
		if(course == null){
			return RestResponse.bad(-1, "course not found!");
		}
		
		return RestResponse.good(course);
	}
	
	/**
	 * 根据课程分类获取课程列表
	 * @param classification
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, params = {"classification"})
	public RestResponse<List<Course>> getCourseByClassification(@RequestParam (value = "classification", required = true) String classification){
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
			classification = Const.COURSE_UNKNOWN;
		}
		
		
		List<Course> courseList = courseService.getCourseByClassification(classification);
		if(courseList == null || courseList.isEmpty()){
			return RestResponse.bad(-1, "invalid classification: " + classification);
		}
		
		return RestResponse.good(courseList);
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
