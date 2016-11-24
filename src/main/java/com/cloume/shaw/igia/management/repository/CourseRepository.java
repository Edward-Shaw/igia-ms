package com.cloume.shaw.igia.management.repository;

import org.springframework.data.repository.Repository;

import com.cloume.shaw.igia.common.resource.Course;

public interface CourseRepository extends Repository<Course, String> {

	public Course save(Course course);
}
