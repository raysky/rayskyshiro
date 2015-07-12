package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AuthorityMapper;
import com.mvc.model.Authority;
import com.mvc.service.AuthorityService;
/**
 * 
 * @author raysky
 *
 * @date 2014-9-28 ионГ10:35:43
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	private AuthorityMapper authorityMapper;
	
	@Autowired
	public void setAuthorityMapper(AuthorityMapper authorityMapper) {
		this.authorityMapper = authorityMapper;
	}

	@Override
	public List<Authority> query() {
		return authorityMapper.query();
	}

}
