package com.cloume.shaw.igia.management.repository;

import org.springframework.data.repository.Repository;

import com.cloume.shaw.igia.common.resource.Subscribe;

/**
 * Subscribe mongo repository.
 * @author XiaoYu
 *
 */
public interface SubscribeRepository extends Repository<Subscribe, String> {
	
	/**
	 * get subscribe by code.
	 * @param code
	 * @return
	 */
	Subscribe findSubscirbeByCode(String code);
	
	/**
	 * save subscribe.
	 * @param subscribe
	 * @return
	 */
	Subscribe save(Subscribe subscribe);
}
