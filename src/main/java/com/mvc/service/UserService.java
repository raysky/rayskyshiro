package com.mvc.service;

import java.util.Map;

import com.mvc.model.User;
import com.mvc.utils.Page;
/**
 * 
 * @author raysky
 *
 */
public interface UserService {
	
	public User queryUniqueByUsername(String username);
	
	public Page<User> queryPage(Page<User> page,Map<String, Object> map);
	
	public void markLocked(String[] id);
	
	public void markNotLocked(String[] id);
	
	public void saveRoles(User user);
	
	public void save(User user);
	
	public void delete(String id);
	
	public void deleteRoles(String id);
	
	public User lazyGet(String id);
	
	public void update(User user);
}
