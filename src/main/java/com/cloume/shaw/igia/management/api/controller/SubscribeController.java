package com.cloume.shaw.igia.management.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.controller.AbstractController;
import com.cloume.shaw.igia.common.resource.Subscribe;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.repository.SubscribeRepository;

/**
 * Subscribe controller.
 * @author XiaoYu
 *
 */
@RestController
@RequestMapping(value = "/api/subscribe")
public class SubscribeController extends AbstractController {
	
	@Autowired
	private SubscribeRepository subscribeRepository;
	
	/**
	 * get subscribe by code.
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public RestResponse<Subscribe> getSubscribeByCode(@PathVariable("code") String code){
		
		Subscribe subscribe = subscribeRepository.findSubscirbeByCode(code);
		if(subscribe == null){
			return RestResponse.bad(-1, "subscribe not found: " + code);
		}
		
		return RestResponse.good(subscribe);
	}
	
	/**
	 * delete subscribe by code: set state to DELETED.
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public RestResponse<Subscribe> deleteSubscribeByCode(@PathVariable("code") String code){
		Subscribe subscribe = subscribeRepository.findSubscirbeByCode(code);
		if(subscribe == null){
			return RestResponse.bad(-1, "subscribe not found: " + code);
		}
		
		subscribe.setState(Const.STATE_DELETED);
		subscribeRepository.save(subscribe);
		
		return RestResponse.good(subscribe);
	}
	
	/**
	 * update Subscribe by code.
	 * @param code
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public RestResponse<Subscribe> updateSubscribeByCode(@PathVariable("code") String code,
			@RequestBody Map<String, Object> body){
		Subscribe subscribe = subscribeRepository.findSubscirbeByCode(code);
		if(subscribe == null){
			return RestResponse.bad(-1, "subscribe not found: " + code);
		}
		
		System.out.println(body);
		for(String key : body.keySet()){
			if(key.compareToIgnoreCase("courses") == 0){
				String[] courses = body.get("courses").toString().split(" ");
				ArrayList<String> list = new ArrayList<String>(Arrays.asList(courses));
				subscribe.setCourses(list);
			}else if(key.compareToIgnoreCase("username") == 0){
				subscribe.getUser().setUsername(body.get("username").toString());
			}else if(key.compareToIgnoreCase("mobile") == 0){
				subscribe.getUser().setMobile(body.get("mobile").toString());
			}else if(key.compareToIgnoreCase("address") == 0){
				subscribe.getUser().setAddress(body.get("address").toString());
			}else if(key.compareToIgnoreCase("subscribeClass") == 0){
				subscribe.setSubscribeClass(body.get("subscribeClass").toString());
			}else if(key.compareToIgnoreCase("state") == 0){
				subscribe.setState(body.get("state").toString());
			}else{
				System.out.println("Wrong key: " + key);
			}
		}
		
		subscribeRepository.save(subscribe);

		return RestResponse.good(subscribe);
	}
	
	/**
	 * add Subscribe.
	 * @param body
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public RestResponse<Subscribe> addSubscribe(@RequestBody Map<String, Object> body){
		Subscribe subscribe = new Subscribe();
		subscribe.setCreateTime(System.currentTimeMillis());
		subscribe.setSubscribeTime(System.currentTimeMillis());
		Subscribe.SimpleUser user = subscribe.new SimpleUser("default value", "default value", "default value", "default value");
		subscribe.setUser(user);
		for(String key : body.keySet()){
			if(key.compareToIgnoreCase("courses") == 0){
				String[] courses = body.get("courses").toString().split(" ");
				ArrayList<String> list = new ArrayList<String>(Arrays.asList(courses));
				subscribe.setCourses(list);
			}else if(key.compareToIgnoreCase("username") == 0){
				subscribe.getUser().setUsername(body.get("username").toString());
			}else if(key.compareToIgnoreCase("mobile") == 0){
				subscribe.getUser().setMobile(body.get("mobile").toString());
			}else if(key.compareToIgnoreCase("address") == 0){
				subscribe.getUser().setAddress(body.get("address").toString());
			}else if(key.compareToIgnoreCase("subscribeClass") == 0){
				subscribe.setSubscribeClass(body.get("subscribeClass").toString());
			}else if(key.compareToIgnoreCase("state") == 0){
				subscribe.setState(body.get("state").toString());
			}else{
				System.out.println("Wrong key: " + key);
			}
		}
		subscribe.setCode(generateSubscribeCode());
		subscribe = subscribeRepository.save(subscribe);
		
		return RestResponse.good(subscribe);
	}
	
	private String generateSubscribeCode(){
		Calendar now = Calendar.getInstance();
		String time = (now.get(Calendar.YEAR) + "").substring(2) + String.format("%02d", now.get(Calendar.MONTH) + 1);
		final String prefix = "IGIA-" + time;
		final String pattern = prefix + "-\\d{1,5}";
		
		String code = "";
		synchronized(this){
			long count = getMongoTemplate().count(Query.query(Criteria.where("code").regex(pattern)), Subscribe.class, "subscribe") + 1;
			code = prefix + "-" + count;
		}
		
		return code;
	}

}
