package com.mvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.mvc.dao.RoleMapper;
import com.mvc.model.Role;
import com.mvc.model.User;
import com.mvc.service.RoleService;
import com.mvc.service.UserService;
import com.mvc.utils.JsonMessage;
import com.mvc.utils.MD5HashUtils;
import com.mvc.utils.Page;
import com.mvc.utils.Webs;
@Controller
@SuppressWarnings("unused")
@RequestMapping("/security/user")
public class SecurityUserController extends MultiActionController{
	private static final String REDIRECT_LIST = "redirect:/security/user/list/";
	private UserService userService;
	private RoleService roleService;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<User> page, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		page = userService.queryPage(page,map);
		model.addAttribute(page);
		return "/security/user/list";
	}
	
	@RequestMapping(value = "/lock/", method = RequestMethod.POST)
	@ResponseBody
	public JsonMessage lock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}
			
			String[] items = findItems(request);
			if(null != items && items.length>0)
			userService.markLocked(items);
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}
	
	@RequestMapping(value = "/not-lock/", method = POST)
	@ResponseBody
	public JsonMessage notlock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!Webs.isAjax(requestWith)) {
				return JsonMessage.one().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			if(null != items && items.length>0)
			userService.markNotLocked(items);
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}
	
	@RequestMapping(value = "/create/", method = GET)
	public String create(Model model) {
		List<Role> roles = roleService.query();
		model.addAttribute(new User()).addAttribute("roles", roles);
		return "/security/user/form";
	}
	
	@RequestMapping(value = "/create/", method = POST)
	public String create(@Valid User entity, BindingResult result) {
		if (result.hasErrors()) {
			return REDIRECT_LIST;
		}
		try {
			entity.setId(MD5HashUtils.getRandomGUID());
			entity.encodePassword();
			userService.save(entity);
			userService.saveRoles(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return REDIRECT_LIST;
	}
	
	@RequestMapping(value = "/delete/", method = POST)
	public String delete(HttpServletRequest request) {
		for (String item : findItems(request)) {
			userService.deleteRoles(item);
			userService.delete(item);
		}
		return REDIRECT_LIST;
	}
	
	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		User entity = userService.lazyGet(id);
		List<Role> roles = roleService.query();
		model.addAttribute(entity.ofRoles()).addAttribute("roles", roles).addAttribute("_method", "PUT");
		return 	"/security/user/form";
	}
	
	@RequestMapping(value = "/{id}/edit/", method = POST)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			User entity = userService.lazyGet(id);
			entity.getRolesTrans().clear();

			String oldUsername = entity.getUsername();
			String oldPassword = entity.getPassword();
			
			bind(request, entity);

			if (entity.hasPassword()) {
				entity.encodePassword();
			}
			else {
				entity.setPassword(oldPassword);
			}
			userService.update(entity);
			userService.deleteRoles(entity.getId());
			userService.saveRoles(entity);
			
		} catch (Exception e) {
			
		}

		return REDIRECT_LIST;
	}

	private JsonMessage onSuccess() {
		return JsonMessage.one().success();
	}

	private JsonMessage onFailure(String msg) {
		return JsonMessage.one().error().message(msg);
	}

	private JsonMessage onFailure(Exception e) {
		return JsonMessage.one().error().message(e.getMessage());
	}
	private String[] findItems(HttpServletRequest request) {
		String itemsAsString = request.getParameter("items");
		return StringUtils.split(itemsAsString, ',');
	}
}
