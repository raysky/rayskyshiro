package com.mvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mvc.model.Role;
/**
 * 
 * @author raysky
 *
 */
@Repository
public interface RoleMapper {
	
	public List<Role> query();
	
	public List<Role> queryPage(Map<String, Object> map);
	
	public void save(Role role);
	
	public void saveAuthorities(Map<String, Object> map);
	
	public void update(Role role);
	
	public void deleteAuthorities(String id);
	
	public void delete(String id);
	
	public Role get(String id);
	
	public long countRole(Map<String, Object> map);
	
	
}
