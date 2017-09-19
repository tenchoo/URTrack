/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.web.web.account;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.urt.dto.UserDto;
/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
//@Controller
//@RequestMapping(value = "/login")
public class LoginControllerDemo {
	private static Logger logger = LoggerFactory.getLogger(LoginControllerDemo.class);

	
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/welcome",method = RequestMethod.GET)
	public String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "/loginSuccess",method = RequestMethod.GET)
	public String loginSuccess(HttpSession httpSession) {
		UserDto user = (UserDto) httpSession.getAttribute("sessionUser");
		if("jiliadmin".equals(user.getLoginName())){
			return "jiliIndex";
		}
		return "index";
	}
	
	 /** 
     * 用户登出 
     */  
    @RequestMapping("/logout")  
    public String logout(HttpServletRequest request){  
         SecurityUtils.getSubject().logout();  
         request.getSession().invalidate();
         return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";  
    }
	
	/************************************局部刷新demo start*****************************************/
	@RequestMapping(value="/queryFeedBackInfo",method = RequestMethod.GET)
	public String queryFeedBackInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
	    return "localRefreshDemo/bootStropDemo1";
	}

	/************************************局部刷新demo end*****************************************/
	
	@RequestMapping(value = "/articleList",method = RequestMethod.GET)
	public String articleList() {
		return "article-list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
