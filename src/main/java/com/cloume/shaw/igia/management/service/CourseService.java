package com.cloume.shaw.igia.management.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cloume.shaw.igia.common.resource.Course;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.management.iservice.AbstractServiceBase;
import com.cloume.shaw.igia.management.iservice.ICourseService;

@Service
public class CourseService extends AbstractServiceBase implements ICourseService {

	@Override
	public void addNewCourse(Course course) {
		
		getMongoTemplate().save(course);
	}

	@Override
	public List<Course> listByPage(String state, String classification, String createdTime, int[] page) {
		if (page == null) {
			page = new int[2];
			page[0] = 0;
			page[1] = 20;
		}

		// 获取页码及每一页的长度
		int pageNumber = page[0];
		int pageSize = page[1];
		
		Query query = new Query();
		Criteria criterion = new Criteria();
		if(state.compareToIgnoreCase("default") != 0){
			criterion.and("state").is(state);
		}else{
			criterion.and("state").ne(Const.STATE_DELETED);
		}
		
		if(classification.compareToIgnoreCase("default") != 0){
			criterion.and("classification").is(classification);
		}
		
		if (createdTime.compareToIgnoreCase("default") != 0) {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.MILLISECOND, 0);
			long datetime;
			switch (createdTime) {
			case "today":
				datetime = now.getTimeInMillis();
				criterion = criterion.and("createTime").gte(datetime);
				break;
			case "yesterday":
				datetime = now.getTimeInMillis();
				now.add(Calendar.DATE, -1);
				long datetime1 = now.getTimeInMillis();
				criterion = criterion.and("createTime").gte(datetime1).lte(datetime);
				break;
			case "this_week":
				now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				datetime = now.getTimeInMillis();
				criterion = criterion.and("createTime").gte(datetime);
				break;
			case "this_month":
				now.set(Calendar.DAY_OF_MONTH, 1);
				datetime = now.getTimeInMillis();
				criterion = criterion.and("createTime").gte(datetime);
				break;
			}
		}

		// 查询"state"不等于"STATE_DELETED"的预约，跳过前 pageNumber*pageSize条记录，返回之后的pageSize条记录
		query = Query.query(criterion).skip(pageNumber * pageSize).limit(pageSize);

		// 根据预约id倒序排列
		List<Course> courseList = getMongoTemplate().find(query.with(new Sort(Direction.DESC, "_id")), Course.class);
		
		return courseList;
	}

	@Override
	public Course deleteCourseById(String id) {
		Course course = getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(id)), Course.class);
		if(course != null ){
			course.setState(Const.STATE_DELETED);
			getMongoTemplate().save(course);
		}
		
		return course;
	}

	@Override
	public Course findCourseById(String id) {
		Course course = getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(id)), Course.class);
		
		return course;
	}

	@Override
	public List<Course> getCourseByClassification(String classification) {
		List<Course> courseList = getMongoTemplate().find(Query.query(Criteria.where("classification").is(classification)), Course.class);
		
		return courseList;
	}

	@Override
	public Course findCouseByCode(String code) {
		Course course = getMongoTemplate().findOne(Query.query(Criteria.where("code").is(code)), Course.class);
		
		return course;
	}

}
