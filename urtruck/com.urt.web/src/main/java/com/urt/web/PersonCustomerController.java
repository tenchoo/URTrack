package com.urt.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoSsAccessLogDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.traffic.TrafficQueryService;

@Controller
@RequestMapping(value="/personCustomer")
public class PersonCustomerController {
	@Autowired
	private TrafficQueryService trafficQueryService;
	@Autowired
	private UserService userService;
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest req) {
		ModelAndView mv=new ModelAndView("cust/personCustomerIndex");
		mv.addObject("123","123");
		LaoSsAccountDto currentUser=(LaoSsAccountDto)req.getSession().getAttribute("sessionUser");
		mv.addObject("currentUser",currentUser);
		String iccid = userService.getIccidByCustId(currentUser.getCustId()).get(0);
		//当前流量包余额
		TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
		String dataRemaining = doNowTrafficQuery.getDataRemaining();
		//未发送流量包
		
		//iccid卡月使用情况
		trafficQueryService.doMonthTrafficQuery(iccid,"");
		return mv;
	}
	@RequestMapping(value="/test")
	public ModelAndView test() {
		ModelAndView mv=new ModelAndView("test");
		return mv;
	}
	@RequestMapping(value="/demo")
	public ModelAndView demo() {
		ModelAndView mv=new ModelAndView("demo");
		return mv;
	}
}
