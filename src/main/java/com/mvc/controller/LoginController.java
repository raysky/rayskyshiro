package com.mvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.dao.UserMapper;
import com.mvc.exception.InvalidCaptchaException;
import com.mvc.utils.MD5HashUtils;
/**
 * 
 * @author raysky
 *
 */
@Controller
public class LoginController {
	public static final int UNKNOWN_ACCOUT_ERROR_CODE = 1;
	public static final int LOCKED_ACCOUT_ERROR_CODE = 2;
	public static final int AUTHENTICATION_ERROR_CODE = 4;
	public static final int INVALID_CAPTCHA_ERROR_CODE = 8;
	public static final int OTHER_ERROR_CODE = 16;
	private UserMapper userDaoImpl;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public UserMapper getUserDaoImpl() {
		return userDaoImpl;
	}

	@Autowired
	public void setUserDaoImpl(UserMapper userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET )
	public String login(){
		return "/admin/login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(HttpSession session,HttpServletRequest request){
		logger.debug(String.format(
				"Handle login request with session[id=%s,createOn=%s,lastAccessedOn=%s]", 
				session.getId(),
				session.getCreationTime(),
				session.getLastAccessedTime()
			));
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		try {
			checkCaptcha(request, session);
			AuthenticationToken token = createToken(request);
			subject.login(token);
		} catch (Exception e) {
			logger.error("login occur exception.", e);
			return "redirect:/login?code=" + translateException(e);
		}
		
		return "redirect:/admin/dashboard";
	}
	
	@RequestMapping(value = "/admin/dashboard", method = GET)
	public String index() {
		return "/admin/index";
	}
	
	/**
	 * must supported any method
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/deny")
	public String deny() {
		return "admin/deny";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/logout", method = { GET, POST })
	public String logout() {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
	private void checkCaptcha(HttpServletRequest request, HttpSession session) {
		String c = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
        String parm = (String) request.getParameter("captcha"); 
        if (StringUtils.isNotBlank(parm)&&StringUtils.isNotBlank(parm)) {
			if (c.equalsIgnoreCase(parm)) {
				
			}else {
				throw new InvalidCaptchaException();
			}
		}else {
			throw new InvalidCaptchaException();
		}
	}
	private int translateException(Exception e) {

		if (e instanceof InvalidCaptchaException) {
			return INVALID_CAPTCHA_ERROR_CODE;
		}

		if (e instanceof UnknownAccountException) {
			return UNKNOWN_ACCOUT_ERROR_CODE;
		}

		if (e instanceof LockedAccountException) {
			return LOCKED_ACCOUT_ERROR_CODE;
		}

		if (e instanceof AuthenticationException) {
			return AUTHENTICATION_ERROR_CODE;
		}

		return OTHER_ERROR_CODE;
	}
	
	private AuthenticationToken createToken(HttpServletRequest request) {
		String username = WebUtils.getCleanParam(request, "username");
		String password = WebUtils.getCleanParam(request, "password");

		String passwordAsMd5 = MD5HashUtils.asMD5(password, username);
		String rememberMeAsString = WebUtils.getCleanParam(request, "rememberMe");
		boolean rememberMe = false;
		if (null != rememberMeAsString) {
			rememberMe = Boolean.valueOf(rememberMeAsString);
		}
		String host = request.getRemoteHost();
		return new UsernamePasswordToken(username, passwordAsMd5, rememberMe, host);
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/head/", method = GET)
	public String head() {
		return "admin/head";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/foot/", method = GET)
	public String foot() {
		return "admin/foot";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/right/", method = GET)
	public String right(Model model) {
		Properties props = System.getProperties();
		model.addAttribute("props", props);
		return "admin/right";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/sys/", method = GET)
	public String sys() {
		return "admin/sys";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/content/", method = GET)
	public String content() {
		return "admin/content";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/resource/", method = GET)
	public String resource() {
		return "admin/resource";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/conf/", method = GET)
	public String conf() {
		return "admin/conf";
	}
}
