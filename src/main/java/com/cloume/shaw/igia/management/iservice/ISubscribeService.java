package com.cloume.shaw.igia.management.iservice;

import java.util.List;

import com.cloume.shaw.igia.common.resource.Subscribe;

public interface ISubscribeService {
	
	/**
	 * get subscribe list by page
	 * @param state
	 * @param createTime
	 * @param subscribeTime
	 * @param page [0, 1], [page, size]
	 * @return
	 */
	public List<Subscribe> listByPage(String state, String createTime, String subscribeTime, int[] page);
}
