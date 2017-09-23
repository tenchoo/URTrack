package com.urt.web.reports;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.interfaces.report.ReportFormService;
import com.urt.interfaces.reports.CardCycleService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.web.homePage.AdminHomePageController;

@Controller
@RequestMapping("/card")
public class CardCycleController {
	
	private static final Logger log = Logger.getLogger(CardCycleController.class);
	@Autowired
	private CardCycleService cardCycleservice;
	//跳转销售报表页面
	@RequestMapping("/cycle")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/reportsIndex/cardcycle");
		return mv;
	}
	@ResponseBody
	@RequestMapping("/queryCycle")
	public List<Map<String,Object>> queryCyle(HttpServletRequest req,HttpServletResponse resp,LaoUserDto userDto) {
		if (!ActionUtil.ifSuperUser(req)) {// 不是已登陆客户或者其渠道发展客户，不展示剩余流量
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			Long custId = user.getCustId();
			userDto.setChannelCustId(custId);
		}
		List<Map<String,Object>> stateList=cardCycleservice.queryCardsCycle(userDto);
		log.info("stateList:"+stateList);
		return stateList;
	}
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByState")
	public Map<String,Object> doAjaxHomePageByState(HttpServletRequest req,LaoUserDto userDto)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if (!ActionUtil.ifSuperUser(req)) {// 不是已登陆客户或者其渠道发展客户，不展示剩余流量
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			Long custId = user.getCustId();
			userDto.setChannelCustId(custId);
		}
		List<Map<String, Object>> list = cardCycleservice.selectCountByState(userDto);
		if (list != null && list.size() > 0) {
			int count = 0;
			String[] data1 = new String[list.size()+1];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String staticName = mapi.get("STATIC_NAME").toString();
				count = count + sumi;
				data1[i] = staticName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", staticName);
				data2[i] = mapData2;
			}
			data1[list.size()] = "";//"系统总用户数："+count+"";
			DecimalFormat decimalFormat = new DecimalFormat(",###");// 格式化设置
			String countStr = decimalFormat.format(count);//299,792,458 
			map.put("countStr", countStr);
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
}
