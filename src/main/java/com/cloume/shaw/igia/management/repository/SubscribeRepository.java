package com.cloume.shaw.igia.management.repository;

import org.springframework.data.repository.Repository;

import com.cloume.shaw.igia.common.resource.Subscribe;

public interface SubscribeRepository extends Repository<Subscribe, String> {
	
	Subscribe findSubscirbeByCode(String code);
}
