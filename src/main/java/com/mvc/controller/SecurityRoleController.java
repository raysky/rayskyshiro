package com.mvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.mvc.model.Authority;
import com.mvc.model.Role;
import com.mvc.service.AuthorityService;
import com.mvc.service.RoleService;
import com.mvc.utils.Page;

/**
 * 
 * @author raysky
 * 
 */
@Controller
@RequestMapping("/security/role")
public class SecurityRoleController extends MultiActionController {

	private static final String REDIRECT_LIST = "redirect:/security/role/list/";

	private RoleService roleService;
	
	private AuthorityService authorityService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<Role> page, Model model) {
		page = roleService.queryPage(page);
		model.addAttribute(page);
		return "/security/role/list";
	}

	@RequestMapping(value = "/create/", method = GET)
	public String create(Model model) {
		List<Authority> authorities = authorityService.query();
		model.addAttribute(new Role()).addAttribute("authorities", authorities);
		return "/security/role/form";
	}

	@RequestMapping(value = "/create/", method = POST)
	public String create(@Valid Role entity, BindingResult result) {
		if (result.hasErrors()) {
			//error("创建用户角色失败，请核对数据后重试");
			return REDIRECT_LIST;
		}
		
		//entity.create();
		//success("用户角色创建成功");
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Role entity = roleService.get(id);
		List<Authority> authorities = authorityService.query();
		model.addAttribute(entity.ofAuths()).addAttribute("authorities", authorities).addAttribute("_method", "PUT");
		return "/security/role/form";
	}

	@RequestMapping(value = "/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Role entity = roleService.get(id);
			entity.getAuthoritiesTrans().clear();

			bind(request, entity);
			//checkIdNotModified(id, entity.getId());
			//entity.modify();
			//success("用户角色修改成功，请刷新该角色关联用户缓存，以使修改生效");
		} catch (Exception e) {
			//error("用户角色修改失败，请核对数据后重试");
		}

		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		//roleService.get(id).delete();
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : findItems(request)) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	
	private String[] findItems(HttpServletRequest request) {
		String itemsAsString = request.getParameter("items");
		return StringUtils.split(itemsAsString, ',');
	}
}
