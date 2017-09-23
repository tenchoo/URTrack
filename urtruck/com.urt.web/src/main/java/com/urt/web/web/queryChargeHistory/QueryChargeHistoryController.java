package com.urt.web.web.queryChargeHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.chargeOff.ChargeOffService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.POIExcelUtil;
@Controller
@RequestMapping("/chargeQuery")
public class QueryChargeHistoryController {
	private static final Logger log = Logger.getLogger(QueryChargeHistoryController.class);
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	
	@Autowired
	private ChargeOffService chargeOffService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/chargeQuery/index");
		List<LaoCustomerDto> queryAllAgents = chargeOffService.queryAllAgents();
		mv.addObject("agents",queryAllAgents);
		boolean right = ActionUtil.ifSuperUser(request);
		mv.addObject("right",right);
		return mv;
	}

	//充值记录查询
	@ResponseBody
	@RequestMapping("/queryChargeHistory")
	public Map<String,Object> queryChargeHistory(HttpServletRequest request, String custId, String month)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(month)){
			resultMap.put("data", new ArrayList<TradeFeeSubDto>());
			resultMap.put("iTotalRecords", 10);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
			return resultMap;
		}
		Calendar calendar = Calendar.getInstance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = sdf.parse(month);
		calendar.setTime(date);
		
		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//按你的要求设置时间
		calendar.set( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), maxDay);
		//按格式输出
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = format.format(calendar.getTime()); //月份最后一天
		
		int minDay=calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		//按你的要求设置时间
		calendar.set( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), minDay);
		String startTime = format.format(calendar.getTime());//月份第一天
		
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		
		if(StringUtils.isBlank(custId) || ("-1").equals(custId)){
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			if(user != null){
				custId = user.getCustId().toString();
			}
		}
		
		log.info("custId************"+custId+"startTime************"+startTime+"endTime************"+endTime+"currentPage*************"+pageNo);
		List<TradeFeeSubDto> tradeFeeSubDtoList = tradeFeeSubService.queryTradeFeeSubByCustId(custId, startTime, endTime, pageNo, pageSize);
		if(tradeFeeSubDtoList != null && tradeFeeSubDtoList.size() > 0){
			int total = tradeFeeSubService.countTotal(custId, startTime, endTime);
			resultMap.put("data", tradeFeeSubDtoList);
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", total);// 总记录数
		}else{
			resultMap.put("data", new ArrayList<TradeFeeSubDto>());
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
		}
		return resultMap;
	}
	
	
	/**
	 * @throws ParseException 
	* 功能描述：导出 订单
	* @author sunhao
	* @date 2017年4月8日 下午2:43:13
	* @param @param dto
	* @param @param startDate
	* @param @param endDate
	* @param @param request
	* @param @param response
	* @return void
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/exportOrder")
	public void exportRevenue(String custId, String month, HttpServletRequest request, HttpServletResponse response) throws ParseException{
		POIExcelUtil util = new POIExcelUtil();
		List<TradeFeeSubDto> list = null;
		
		Calendar calendar = Calendar.getInstance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = sdf.parse(month);
		calendar.setTime(date);
		
		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//按你的要求设置时间
		calendar.set( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), maxDay);
		//按格式输出
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = format.format(calendar.getTime()); //月份最后一天
		
		int minDay=calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		//按你的要求设置时间
		calendar.set( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), minDay);
		String startTime = format.format(calendar.getTime());//月份第一天
		
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		if(StringUtils.isBlank(custId) || ("-1").equals(custId)){
			if(user != null){
				custId = user.getCustId().toString();
			}
		}
		int total = tradeFeeSubService.countTotal(custId, startTime, endTime);
		log.info("custId************"+custId+"startTime************"+startTime+"endTime************"+endTime+"currentPage*************"+pageNo);
		list = tradeFeeSubService.queryTradeFeeSubByCustId(custId, startTime, endTime, pageNo, total);
		
		Map<String,String> maps = null;
		if(list != null && list.size() > 0){
			maps = new HashMap<String,String>();
			maps.put("custName", "客户名称");
			maps.put("iccid", "号卡（ICCID）");
			maps.put("goodsName", "产品名称");
			maps.put("fee", "缴费金额(元)");
			maps.put("pagNumber", "数量");
			boolean right = ActionUtil.ifSuperUser(request);
			if(right){
				maps.put("goodsPrices", "单价(元)");
			}else{
				maps.put("payDateStr", "订购时间");
				maps.put("orderResult", "订购结果");
			}
			
			String excelFileName = user.getLoginName()+"查询"+month+new Date().getTime()+"订购记录.xls";
			util.excelExport(maps, list, excelFileName, response);
		}
	}
}
