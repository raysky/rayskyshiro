package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.model.Role;
import com.mvc.utils.Page;

/**
 * 
 * @author raysky
 * 
 */
public interface RoleService {

	public Page<Role> queryPage(Page<Role> page);

	public List<Role> query();

	public void save(Role role);

	public void saveAuthorities(Map<String, Object> map);

	public void update(Role role);

	public void deleteAuthorities(String id);

	public void delete(String id);

	public Role get(String id);
}
