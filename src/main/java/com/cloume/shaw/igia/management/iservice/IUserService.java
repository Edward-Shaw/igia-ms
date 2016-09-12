package com.cloume.shaw.igia.management.iservice;

import java.util.List;

import com.cloume.shaw.igia.management.resource.User;

public interface IUserService {
	
	/**
	 * get use list by page
	 * @param banned
	 * @param type
	 * @param time
	 * @param page [0, 1], [page, size]
	 * @return
	 */
	public List<User> listByPage(String banned, String type, String time, int[] page);
}
