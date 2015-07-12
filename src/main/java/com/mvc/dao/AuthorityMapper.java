package com.mvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mvc.model.Authority;

@Repository
public interface AuthorityMapper {
	
	public List<Authority> query();
	
}
