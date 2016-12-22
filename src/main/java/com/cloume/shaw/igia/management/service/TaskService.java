package com.cloume.shaw.igia.management.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cloume.shaw.igia.common.resource.Task;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.management.iservice.AbstractServiceBase;
import com.cloume.shaw.igia.management.iservice.ITaskService;

@Service
public class TaskService extends AbstractServiceBase implements ITaskService {

	@Override
	public void addNewTask(Task task) {
		
		getMongoTemplate().save(task);
	}

	@Override
	public List<Task> listByPage(String state, String course, String createdTime, int[] page) {
		if(page == null){
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
		
		if(course.compareToIgnoreCase("default") != 0){
			criterion.and("courseName").is(course);
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
		List<Task> taskList = getMongoTemplate().find(query.with(new Sort(Direction.DESC, "_id")), Task.class);
		
		return taskList;
	}

}
