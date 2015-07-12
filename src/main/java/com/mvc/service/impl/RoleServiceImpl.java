package com.mvc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.RoleMapper;
import com.mvc.model.Role;
import com.mvc.service.RoleService;
import com.mvc.utils.Page;
/**
 * 
 * @author raysky
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	private RoleMapper roleDao;
	
	@Autowired
	public void setRoleDao(RoleMapper roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public Page<Role> queryPage(Page<Role> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagesize", page.getPageSize());
		map.put("offset", (page.getPageNo()-1)*page.getPageSize());
		map.put("showName", page.getParams());
		List<Role> users = roleDao.queryPage(map);
		int totalPages = 0;
		long  totalCount = roleDao.countRole(map);
		if(0!=totalCount){
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
	public List<Role> query() {
		return null;
	}

	@Override
	public void save(Role role) {

	}

	@Override
	public void saveAuthorities(Map<String, Object> map) {

	}

	@Override
	public void update(Role role) {

	}

	@Override
	public void deleteAuthorities(String id) {

	}

	@Override
	public void delete(String id) {

	}

	@Override
	public Role get(String id) {
		return roleDao.get(id);
	}

}
