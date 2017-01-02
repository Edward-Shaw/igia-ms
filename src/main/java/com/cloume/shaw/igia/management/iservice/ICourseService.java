package com.cloume.shaw.igia.management.iservice;

import java.util.List;

import com.cloume.shaw.igia.common.resource.Course;

public interface ICourseService {
	
	/**
	 * 添加新的课程
	 * @param course
	 * @return
	 */
	public void addNewCourse(Course course);

	/**
	 * 根据筛选条件获取课程列表
	 * @param state
	 * @param classfication
	 * @param createdTime
	 * @param page
	 * @return
	 */
	public List<Course> listByPage(String state, String classfication, String createdTime, int[] page);

	/**
	 * 将指定id的课程状态修改为删除状态
	 * @param id
	 * @return
	 */
	public Course deleteCourseById(String id);
	
	/**
	 * 根据指定id获取课程详情
	 * @param id
	 * @return
	 */
	public Course findCourseById(String id);

	/**
	 * 根据指定分类@classification获取课程列表
	 * @param classification
	 * @return
	 */
	public List<Course> getCourseByClassification(String classification);

	/**
	 * get Course by code.
	 * @param code
	 * @return
	 */
	public Course findCouseByCode(String code);
}
