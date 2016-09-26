package com.cloume.shaw.igia.management.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cloume.shaw.igia.management.iservice.AbstractServiceBase;
import com.cloume.shaw.igia.management.iservice.ISubscribeService;
import com.cloume.shaw.igia.management.resource.Subscribe;

@Service
public class SuscribeService extends AbstractServiceBase implements ISubscribeService {

	@Override
	public List<Subscribe> listByPage(String state, String createTime, String subscribeTime, int[] page) {
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
		}
		
		if (createTime.compareToIgnoreCase("default") != 0) {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.MILLISECOND, 0);
			long datetime;
			switch (createTime) {
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
		
		//TODO:根据预约的活动时间subscribe_time来筛选查询
		
		// 查询"state"不等于"STATE_DELETED"的预约，跳过前 pageNumber*pageSize条记录，返回之后的pageSize条记录
		query = Query.query(criterion).skip(pageNumber * pageSize).limit(pageSize);

		// 根据预约id倒序排列
		List<Subscribe> subscribeList = getMongoTemplate().find(query.with(new Sort(Direction.DESC, "_id")), Subscribe.class);
		
		return subscribeList;
	}

}
