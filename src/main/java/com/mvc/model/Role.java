package com.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


/**
 * 
 * @author raysky
 * 
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String showName;

	private transient Map<String, String> authoritiesTrans = Maps.newHashMap();
	private Set<Authority> authorities = Sets.newHashSet();

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public String getShowName() {
		return showName;
	}

	public Role setShowName(String showName) {
		this.showName = showName;
		return this;
	}

	public Map<String, String> getAuthoritiesTrans() {
		return authoritiesTrans;
	}

	public void setAuthoritiesTrans(Map<String, String> authoritiesTrans) {
		this.authoritiesTrans = authoritiesTrans;
	}

	public Set<Authority> getAuthorities() {
		if (null == authorities) {
			return Collections.emptySet();
		}

		return authorities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasAuthority() {
		return !getAuthorities().isEmpty();
	}

	public Role setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
		return this;
	}

	/**
	 * transit current authorities to authTrans map
	 * 
	 * @return
	 */
	public Role ofAuths() {
		Map<String, String> authTrans = Maps.newHashMap();
		Iterator<Authority> iterator = getAuthorities().iterator();
		while (iterator.hasNext()) {
			Authority authority = iterator.next();
			authTrans.put(authority.getId(), authority.getId());
		}
		setAuthoritiesTrans(authTrans);
		return this;
	}

	/**
	 * merge authority from authTrans map
	 * 
	 * @return
	 */
	protected Role mergeAuths() {
		if (null == getAuthoritiesTrans()) {
			return this;
		}

		// clear current authorities first
		getAuthorities().clear();
		for (String authId : getAuthoritiesTrans().values()) {
			if (StringUtils.isBlank(authId)) {
				continue;
			}

			Authority auth = new Authority();
			auth.setId(authId);
			getAuthorities().add(auth);
		}

		return this;
	}

	public String getAuthNamesAsString() {
		if (getAuthorities().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		Iterator<Authority> iterator = getAuthorities().iterator();
		while (iterator.hasNext()) {
			Authority authority = iterator.next();
			buffer.append(authority.getName()).append(",");
		}
		
		return buffer.deleteCharAt(buffer.length() - 1).toString();
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAuthPermissions() {
		if (getAuthorities().isEmpty()) {
			return Collections.emptyList();
		}

		List<String> permissions = new ArrayList<String>();
		Iterator<Authority> iterator = getAuthorities().iterator();
		while (iterator.hasNext()) {
			Authority authority = iterator.next();
			permissions.add(authority.getPermission());
		}
		return permissions;

	}

}
