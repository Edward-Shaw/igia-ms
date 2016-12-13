package com.cloume.shaw.igia.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.controller.AbstractController;
import com.cloume.shaw.igia.common.resource.Subscribe;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.management.repository.SubscribeRepository;

@RestController
@RequestMapping(value = "/api/subscribe")
public class SubscribeController extends AbstractController {
	
	@Autowired
	private SubscribeRepository subscribeRepository;
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public RestResponse<Subscribe> getSubscribeByCode(@PathVariable("code") String code){
		
		Subscribe subscribe = subscribeRepository.findSubscirbeByCode(code);
		if(subscribe == null){
			return RestResponse.bad(-1, "subscribe not found: " + code);
		}
		
		return RestResponse.good(subscribe);
	}

}
