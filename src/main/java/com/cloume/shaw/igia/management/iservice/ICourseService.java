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
}
