package com.cloume.shaw.igia.management.service;

import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.management.iservice.AbstractServiceBase;
import com.cloume.shaw.igia.management.iservice.ICourseService;

public class CourseService extends AbstractServiceBase implements ICourseService {

	@Override
	public void addNewCourse(Course course) {
		
		getMongoTemplate().save(course);
	}

}
