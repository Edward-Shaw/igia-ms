package com.cloume.shaw.igia.management.iservice;

import java.util.List;

import com.cloume.shaw.igia.common.resource.Task;

public interface ITaskService {

	/**
	 * 根据筛选条件获取作业列表
	 * @param state
	 * @param course
	 * @param createdTime
	 * @param page
	 * @return
	 */
	public List<Task> listByPage(String state, String course, String createdTime, int[] page);
	
	/**
	 * 添加新的课程作业
	 * @param task
	 */
	public void addNewTask(Task task);
	
	/**
	 * 根据id查找课程作业
	 * @param id
	 * @return
	 */
	public Task findTaskById(String id);
}
