package com.urt.web.web.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * 小懂上网     登录
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/XdswLogin")
public class XdswLoginController {

	// 登录
	@RequestMapping( value="tologin", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView tologin(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/toLogin");
		return mv;
	}
	
}
