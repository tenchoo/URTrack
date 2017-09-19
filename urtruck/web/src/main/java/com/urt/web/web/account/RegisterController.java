/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.web.web.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.authority.LaoSsAccountService;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private LaoSsAccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "/account/register";
	}
	@RequestMapping(method = RequestMethod.GET, value="/toRegister")
	public String toRegister(String userName,String relatedId, Model model) {
		model.addAttribute("name", userName);
		model.addAttribute("relatedId", relatedId);
		return "/account/lenovoRegister";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String register( LaoSsAccountDto user, RedirectAttributes redirectAttributes) {
		String regex = "^[0-9A-Za-z]{6,20}$";
		if(!user.getLoginName().matches(regex)){
			return "/account/register";
		}
		accountService.saveUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		LaoSsAccountDto dto=accountService.getUserByLoginName(loginName);
		if (dto!= null && dto.getAcconutId()!=null) {
			return "false";
		} else {
			return "true";
		}
	}
}
