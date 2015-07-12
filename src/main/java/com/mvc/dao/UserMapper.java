package com.mvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mvc.model.User;

@Repository
public interface UserMapper {
	
	public User queryUniqueByUsername(String username);
	
	public List<User> queryPage(Map<String, Object> map);
	
	public long countUser(Map<String, Object> map);
	
	public void markLocked(String[] id);
	
	public void markNotLocked(String[] id);
	
	public void saveRoles(Map<String, Object> map);
	
	public void save(User user);
	
	public void delete(String id);
	
	public void deleteRoles(String id);
	
	public User lazyGet(String id);
	
	public void update(User user);
	
	
}
