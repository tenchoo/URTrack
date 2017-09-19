package com.urt.web.card;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoPoolDto;
import com.urt.dto.LaoPoolMemberDto;
import com.urt.dto.LaoPoolUseInfoDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.TagService;
import com.urt.web.common.util.ActionUtil;

@Controller
@RequestMapping(value = "/poolController")
public class PoolController {
	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/poolQuery")
	public ModelAndView poolQuery() {
		List<LaoPoolDto> poolName=userService.queryPoolName();
		ModelAndView mv=new ModelAndView("/card/poolQuery");
		mv.addObject("poolName",poolName);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/poolInfo")
	public Map<String, Object> poolInfo(HttpServletRequest req, HttpServletResponse resp,LaoPoolDto dto) {
		if("-1".equals(dto.getPoolId())){
			dto.setPoolId(null);
		}
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		// admin进入的话显示所有，其他人进入只显示自己
		//帐号信息 
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		//如果是超级管理员的话，就把所有信息展现出来，如果不是的话，就只展示对应企业客户的信息，所有要设置dto的角色
		if (ifSuperUser == false) {
			dto.setEid(String.valueOf(user.getCustId()));
		}
		Map<String, Object> resultMap = userService.querypoolInfo(dto, pageNo, pageSize);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/poolMenberInfo")
	public Map<String, Object> poolMenberInfo(HttpServletRequest req, HttpServletResponse resp,LaoPoolMemberDto dto) {
		if("-1".equals(dto.getPoolId())){
			dto.setPoolId(null);
		}
		log.info("poolController---------------------dto.getPoolId()"+dto.getPoolId());
		log.info("poolController---------------------dto.getMsisdn()"+dto.getMsisdn());
		log.info("poolController---------------------dto.getIccid()"+dto.getIccid());
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		// admin进入的话显示所有，其他人进入只显示自己
		//帐号信息 
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		//如果是超级管理员的话，就把所有信息展现出来，如果不是的话，就只展示对应企业客户的信息，所有要设置dto的角色
		if (ifSuperUser == false) {
			dto.setEid(String.valueOf(user.getCustId()));
		}
		Map<String, Object> resultMap = userService.poolMenberInfo(dto, pageNo, pageSize);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/poolUseInfo")
	public Map<String, Object> poolUseInfo(HttpServletRequest req, HttpServletResponse resp,LaoPoolUseInfoDto dto) {
		if("-1".equals(dto.getPoolId())){
			dto.setPoolId(null);
		}
		if(dto.getQueryMonth()!=null){
			//拼接月初时间
			String[] monthArr =dto.getQueryMonth().split("-");
			String start=monthArr[0]+monthArr[1]+"01000000";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		    try {
		    	
		    	Date date = sdf.parse(start);
				dto.setMonthStart(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				 //获取这个月有多少天
			    Date date=dto.getMonthStart();
			    Calendar calendar = Calendar.getInstance();  
		        calendar.setTime(date);  
		        int maxdate= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
		        
		        //拼接月末时间
		        String[] monthEndArr =dto.getQueryMonth().split("-");
				String end=monthEndArr[0]+monthArr[1]+String.valueOf(maxdate)+"235959";
				date = sdf.parse(end);
				dto.setMonthEnd(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		// admin进入的话显示所有，其他人进入只显示自己
		//帐号信息 
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		//如果是超级管理员的话，就把所有信息展现出来，如果不是的话，就只展示对应企业客户的信息，所有要设置dto的角色
		if (ifSuperUser == false) {
			dto.setEid(String.valueOf(user.getCustId()));
		}
		Map<String, Object> resultMap = userService.poolUseInfo(dto, pageNo, pageSize);
		return resultMap;
	}
	
	
}
