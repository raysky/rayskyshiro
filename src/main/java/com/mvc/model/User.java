package com.mvc.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mvc.utils.MD5HashUtils;


/**
 * 
 * @author raysky
 * 
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private boolean accountNonLocked = true;
	private String id;

	private transient Map<String, String> rolesTrans = Maps.newHashMap();
	private Set<Role> roles = Sets.newHashSet();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean hasPassword() {
		return StringUtils.isNotBlank(getPassword());
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public User setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
		return this;
	}

	public User lock() {
		return setAccountNonLocked(false);
	}

	public User notLock() {
		return setAccountNonLocked(true);
	}

	public Set<Role> getRoles() {
		if (null == roles) {
			return Collections.emptySet();
		}

		return roles;
	}

	/**
	 * 
	 * @return
	 */
	public Role[] getRolesAsArray() {
		if (null == getRoles() || getRoles().isEmpty()) {
			return new Role[] {};
		}

		return getRoles().toArray(new Role[] {});
	}

	public String getRoleNamesAsString() {
		if (getRoles().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		Iterator<Role> iterator = getRoles().iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			buffer.append(role.getName()).append(",");
		}
		return buffer.deleteCharAt(buffer.length() - 1).toString();
	}

	public User ofRoles() {
		Map<String, String> rolesTrans = Maps.newHashMap();
		Iterator<Role> iterator = getRoles().iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			rolesTrans.put(role.getId(), role.getId());
		}
		setRolesTrans(rolesTrans);
		return this;
	}

	protected User mergeRoles() {
		if (null == getRolesTrans()) {
			return this;
		}

		// clear current roles first
		getRoles().clear();
		for (String roleId : getRolesTrans().values()) {
			if (StringUtils.isBlank(roleId)) {
				continue;
			}

			Role role = new Role();
			role.setId(roleId);
			getRoles().add(role);
		}

		return this;
	}

	public Map<String, String> getRolesTrans() {
		return rolesTrans;
	}

	public User setRolesTrans(Map<String, String> rolesTrans) {
		this.rolesTrans = rolesTrans;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasRole() {
		return !getRoles().isEmpty();
	}

	public User setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getRoleNames() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		List<String> namesList = new LinkedList<String>();
		Iterator<Role> iterator = getRoles().iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			namesList.add(role.getName());
		}
		return new HashSet<String>(namesList);
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getPermissions() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		Set<String> permissions = new HashSet<String>();
		for (Role role : getRoles()) {
			permissions.addAll(role.getAuthPermissions());
		}

		return permissions;
	}


	public User encodePassword() {
		String md5 = MD5HashUtils.asMD5(getPassword(), getUsername());
		return setPassword(md5);
	}


}
