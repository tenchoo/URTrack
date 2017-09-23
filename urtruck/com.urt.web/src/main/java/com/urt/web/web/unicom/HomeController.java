package com.urt.web.web.unicom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lenovo.pay.core.bo.Constant;
import com.lenovo.pay.utils.CookieUtils;

/**
 * 类说明：主界面
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/h5")
public class HomeController {
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/H5/home");
		return mv;
	}
	
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		request.getSession().invalidate();
		CookieUtils.removeCookie(response, Constant.CASHIER_COOKIE_KEY);
		return "redirect:/h5/index";
	}
	
	@RequestMapping("/productIntroduce")
	public ModelAndView productIntroduct() {
		ModelAndView mv = new ModelAndView("/H5/productInfo");
		return mv;
	}
	
}
