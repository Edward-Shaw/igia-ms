package com.cloume.shaw.igia.management.iservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class AbstractServiceBase {
	@Autowired private MongoTemplate mongoTemplate;
	
	protected MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	
	@Autowired private GridFsTemplate gridFsTemplate;
	
	protected GridFsTemplate getGridFsTemplate() {
		return gridFsTemplate;
	}
}
