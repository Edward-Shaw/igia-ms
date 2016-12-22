package com.cloume.shaw.igia.management.api.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloume.shaw.igia.common.controller.AbstractController;
import com.cloume.shaw.igia.common.resource.User;
import com.cloume.shaw.igia.common.rest.RestResponse;
import com.cloume.shaw.igia.common.utils.Const;
import com.cloume.shaw.igia.common.utils.Updater;
import com.cloume.shaw.igia.management.iservice.IUserService;
import com.cloume.shaw.igia.management.repository.UserRepository;
import com.cloume.shaw.igia.management.rest.PagingRestResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends AbstractController{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	final String[] FIELDS = new String[] {"name", "address", "mobile", "email"};
	
	/**
	 * 获取用户列表
	 * @param request
	 * @param page
	 * @param state
	 * @param time
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public PagingRestResponse<User> list(HttpServletRequest request, 
			@RequestParam(value = "page", required = false, defaultValue = "0, 20") int[] page,
			@RequestParam(value = "state", defaultValue = "default") String state,
			@RequestParam(value = "time", defaultValue = "default") String time){
		
		List<User> users = userService.listByPage(true, state, time, page);
		
		return new PagingRestResponse<User>(0, "OK", users);
	}
	
	/**
	 * 获取用户信息详情
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RestResponse<User> getUserDetailById(HttpServletRequest request,
			@PathVariable("id") String id){
		User user = userService.getUserById(id);
		
		return new RestResponse<User>(0, "OK", user);
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @param id
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = {RequestMethod.POST})
	public RestResponse<User> updateUserById(HttpServletRequest request,
			@PathVariable("id") String id,
			@RequestBody Map<String, Object> body){
		User user = getMongoTemplate().findOne(Query.query(Criteria.where("_id").is(id)), User.class);
		if(user == null){
			return RestResponse.bad(-1, "user not found!");
		}
		
		user = new Updater<User>(user).update(body, (key, value) ->{
			if(key.compareToIgnoreCase("banned") == 0){
				if(value.toString().compareToIgnoreCase("true") == 0){
					value = true;
				}else{
					value = false;
				}
			}
			return value;
		});
		user = userRepository.save(user);
		
		return new RestResponse<User>(0, "Ok", user);
	}
	
	/**
	 * 创建新的用户
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.PUT})
	public RestResponse<User> createUser(HttpServletRequest request,
			@RequestBody Map<String, Object> body){
		for (String field : FIELDS) {
			if (!body.containsKey(field)) {
				return RestResponse.bad(-10001, String.format("missed property %s", field), null);
			}
		}
		
		User user = new User(new ObjectId().toString());
		user = new Updater<User>(user).update(body, (key, value)->{
			if(key.compareToIgnoreCase("banned") == 0){
				if(value.toString().compareToIgnoreCase("true") == 0){
					value = true;
				}else{
					value = false;
				}
			}
			return value;
		});
		user.setCode(generateUserCode());
		user = userRepository.save(user);
		
		return RestResponse.good(user);
	}
	
	/**
	 * code = YYMM(年月)-用户数量序号(位数根据数据库中实际用户数量进行动态变更)
	 * 
	 * @return
	 */
	private String generateUserCode() {
		Calendar now = Calendar.getInstance();
		String time = (now.get(Calendar.YEAR) + "").substring(2) + String.format("%02d", now.get(Calendar.MONTH) + 1);
		final String prefix = "U-" + time;
		final String pattern = prefix + "-\\d{1,5}";

		String code = "";
		synchronized (this) {
			long count = getMongoTemplate().count(
					Query.query(Criteria.where("code").regex(pattern).and("state").ne(Const.STATE_DELETED)), User.class,
					"order") + 1;
			code = prefix + "-" + count;
		}

		return code;
	}
}
