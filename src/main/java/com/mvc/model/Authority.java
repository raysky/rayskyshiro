package com.mvc.model;

import java.io.Serializable;



/**
 * 
 * @author raysky
 * 
 */
public class Authority implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	//@Size(min = 1, max = 32)
	private String name;
	//@Size(min = 1, max = 64)
	private String permission;

	public String getName() {
		return name;
	}

	public Authority setName(String name) {
		this.name = name;
		return this;
	}

	public String getPermission() {
		return permission;
	}

	public Authority setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
