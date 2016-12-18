package com.cloume.shaw.igia.management.repository;

import org.springframework.data.repository.Repository;

import com.cloume.shaw.igia.common.resource.User;

public interface UserRepository extends Repository<User, String> {
	
	public User save(User user);
}
