package com.urt.web.userIndex;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.StringUtil;

@Controller
@RequestMapping("/personal")
public class PersonalIndexController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrafficQueryService trafficQueryService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("/userIndex/personalIndex");
		//登陆之后，查询gla用户绑定的iccid
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		if(user != null){
			Long custId = user.getCustId();
			if(custId != null){
				List<String> iccidList = userService.getIccidByCustId(custId);
				mv.addObject("iccidList", iccidList);
			}
			mv.addObject("loginName",user.getLoginName());
		}
		mv.addObject("nowDate", new Date());
		return mv;
	}
	
	@RequestMapping("/queryFlow")
	public void queryFlow(HttpServletRequest request, HttpServletResponse response, String iccid){
		if(iccid != null && iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//先判断用户是否拥有这张卡的操作权限   然后判断用户是否可以订购这个产品  然后判断用户是否可以订购这个产品  
		String custid = null;
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		if(user != null) {
			if(user.getCustId() != null)
				custid = user.getCustId().toString();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(custid != null && userService.hasPermission(iccid, custid)){
			TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
			if(doNowTrafficQuery != null){
				resultMap.put("nacid", doNowTrafficQuery.getState());
				List<PackagePlanDto> listPack = doNowTrafficQuery.getListPack();
				resultMap.put("dataRemaining", doNowTrafficQuery.getDataRemaining());
				if(listPack != null && listPack.size() > 0){
					PackagePlanDto packagePlanDto = listPack.get(0);
					if(StringUtil.isNotBlank(packagePlanDto.getPackageCount()) && StringUtil.isNotBlank(packagePlanDto.getPackageRemain())){
						BigDecimal packageCount = new BigDecimal(packagePlanDto.getPackageCount().replace("M", ""));
						BigDecimal packageRemain=new BigDecimal(packagePlanDto.getPackageRemain().replace("M", ""));
						packageCount = packageCount.subtract(packageRemain);
						resultMap.put("packageCount", packageCount.toString());
					}else{
						resultMap.put("packageCount", 0);
					}
					resultMap.put("packagePlanName", packagePlanDto.getPackageName());
					if(StringUtils.isNotBlank(packagePlanDto.getPackageRemain())){
						resultMap.put("packageRemaining",packagePlanDto.getPackageRemain().replace("M", ""));
					}
					resultMap.put("packageExpirationDate",packagePlanDto.getPackageEndTime());
					
					//详细包信息
					JSONObject result = new JSONObject();
					for (int i = 1; i < listPack.size(); i++) {
						PackagePlanDto pack = listPack.get(i);
						String packageName = pack.getPackageName();
						if(result.containsKey(packageName)){
							result.put(pack.getPackageName(), 1+result.getIntValue(packageName));
						}else{
							result.put(pack.getPackageName(), 1);
						}
					}
					resultMap.put("packageMsg", result);
					
				}else{
					resultMap.put("packagePlanName", 0);
					resultMap.put("packageRemaining",0);
					resultMap.put("packageExpirationDate",sdf.format(new Date()));
					resultMap.put("packageCount", 0);
				}
			}else{
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>trafficQueryService.doNowTrafficQuery(iccid):"+"查询为空"+iccid);
			}
			
			
			//查询本月流量情况
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM");
			Date date = new Date();
			List<TrafficQueryDetailsDto> doMonthTrafficQuery = trafficQueryService.doMonthTrafficQuery(iccid, formate.format(date));
			if(doMonthTrafficQuery != null && doMonthTrafficQuery.size() > 0){
				List<String> dates = new ArrayList<String>();
				List<Double> useList = new ArrayList<Double>();
				for (TrafficQueryDetailsDto trafficQueryDetailsDto : doMonthTrafficQuery) {
					dates.add(trafficQueryDetailsDto.getSessionStartTime());
					if(StringUtils.isNotBlank(trafficQueryDetailsDto.getDataVolume())){
						BigDecimal big = new BigDecimal(trafficQueryDetailsDto.getDataVolume());
						useList.add(big.doubleValue());
					}
				}
				resultMap.put("lables", dates);
				resultMap.put("data", useList);
			}
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			List<TrafficQueryDetailsDto> lastMonth = trafficQueryService.doMonthTrafficQuery(iccid, formate.format(c.getTime()));
			if(lastMonth != null && lastMonth.size() > 0){
				Double total = 0d;
				for (TrafficQueryDetailsDto trafficQueryDetailsDto : lastMonth) {
					BigDecimal big = new BigDecimal(trafficQueryDetailsDto.getDataVolume());
					total = total + big.doubleValue();
				}
				resultMap.put("lastMonCount", total);
			}else{
				resultMap.put("lastMonCount", 0);
			}
		}else{
			resultMap.put("error", "抱歉！该用户没有操作这张卡权限");
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>custid:"+custid+"没有权限"+iccid);
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			Object jsonObject = JSONObject.toJSON(resultMap);
			out.write(jsonObject.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
