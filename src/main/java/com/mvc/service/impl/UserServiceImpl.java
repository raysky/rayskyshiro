package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.UserMapper;
import com.mvc.model.User;
import com.mvc.service.UserService;
import com.mvc.utils.Page;
/**
 * 
 * @author raysky
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@SuppressWarnings("unused")
	private static final Logger loger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserMapper userDaoImpl;
	
	
	@Autowired
	public void setUserDaoImpl(UserMapper userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	@Override
	public User queryUniqueByUsername(String username) {
		return userDaoImpl.queryUniqueByUsername(username);
	}

	@Override
	public Page<User> queryPage(Page<User> page,Map<String, Object> map) {
		map.put("pagesize", page.getPageSize());
		map.put("offset", (page.getPageNo()-1)*page.getPageSize());
		map.put("username", page.getParams());
		List<User> users = userDaoImpl.queryPage(map);
		int totalPages = 0;
		long  totalCount = userDaoImpl.countUser(map);
		if(0!=userDaoImpl.countUser(map)){
			if (totalCount%page.getPageSize()==0) {
				totalPages = (int) (totalCount/page.getPageSize());
			}else {
				totalPages = (int) (totalCount/page.getPageSize()) + 1;
			}
			
		}
		if(page.getPageNo() == 1)page.setIsHasPre(false);
		if (page.getPageNo() == totalPages)page.setIsHasNext(false);
		page.setPrePage(page.getPageNo() - 1);
		page.setNextPage(page.getPageNo() + 1);
		page.setResult(users).setTotalCount(totalCount).setTotalPages(totalPages);
		return page;
	}

	@Override
	public void markLocked(String[] id) {
		userDaoImpl.markLocked(id);
	}

	@Override
	public void saveRoles(User user) {
		List<String> roles = new ArrayList<String>();
		Map<String, String> map = user.getRolesTrans();
		String[] role = new String[map.size()];
		
		for (String key : map.keySet()) {
			roles.add(map.get(key));
		}
		for (int i = 0; i < roles.size(); i++) {
			role[i] = roles.get(i);
		}
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("id", user.getId());
		parames.put("roles", role);
		userDaoImpl.saveRoles(parames);
	}

	@Override
	public void save(User user) {
		userDaoImpl.save(user);
	}

	@Override
	public void delete(String id) {
		userDaoImpl.delete(id);
	}

	@Override
	public void deleteRoles(String id) {
		userDaoImpl.deleteRoles(id);
	}

	@Override
	public void markNotLocked(String[] id) {
		userDaoImpl.markNotLocked(id);
	}

	@Override
	public User lazyGet(String id) {
		return userDaoImpl.lazyGet(id);
	}

	@Override
	public void update(User user) {
		userDaoImpl.update(user);
	}

}
