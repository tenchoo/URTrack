package com.urt.web.web.trafficQuery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.enumeration.CustTypeEnum;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.task.TrafficTaskService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ICCID;

/**
 * 用户流量查询
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/trafficByEnglish")
public class TrafficQueryByEnglishController {

	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	TrafficTaskService  trafficTaskService;

	@RequestMapping(value = "/toQueryView")
	public ModelAndView toQueryView() {
		ModelAndView mv = new ModelAndView("/traffic/trafficQueryByEnglish");
		return mv;
	}

	/**
	 * 按日期查询流量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doTrafficQuery")
	public List<TrafficQueryDetailsDto> doDateTrafficQuery(HttpServletRequest request,HttpServletResponse response) {
		List<TrafficQueryDetailsDto> list = null;
		String iccid = request.getParameter("iccid");
		if (iccid.length() == 19 && "89860616".equals(iccid.substring(0, 8))) {
			iccid = ICCID.replaceLastChar(iccid);
		}
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		if(!ActionUtil.ifSuperUser(request)&&!trafficQueryService.bIsLegalIccId(iccid,custId)){//不是已登陆客户或者其渠道发展客户，不展示剩余流量
			list=new ArrayList<TrafficQueryDetailsDto>();
			TrafficQueryDetailsDto dto=new TrafficQueryDetailsDto();
			dto.setExpMsg("您没有权限查询该ICCID的流量使用信息！");
			dto.setDataVolume("");
			dto.setSessionStartTime("");
			list.add(dto);
			 return list;	
			}
		
		String selectType = request.getParameter("selectType");
		try {
			if ("dayQuery".equals(selectType)) {
				String dayDate = request.getParameter("dayDate");
				list = trafficQueryService.doDayTrafficQuery(iccid, dayDate);
				if (list != null && list.size() > 0) {
					TrafficQueryDetailsDto dto = list.get(list.size()-1);
					dto.setDataVolume("Total："+dto.getDataVolume());
					dto.setSessionStartTime(dto.getSessionStartTime()+"(The Day)");
				} else {
					list = new ArrayList<TrafficQueryDetailsDto>();
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("Total： 0");
					dtoCount.setSessionStartTime(dayDate+"(The Day)");
					list.add(dtoCount);
				}
			} else if ("monthQuery".equals(selectType)) {
				String monthDate = request.getParameter("monthDate");
				list = trafficQueryService.doMonthTrafficQuery(iccid, monthDate);
				double count = 0;
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						TrafficQueryDetailsDto dto = list.get(i);
						count = count + Double.parseDouble(dto.getDataVolume());
					}
					DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("Total："+decimalFormat.format(count));
					dtoCount.setSessionStartTime(monthDate+"(The Month)");
					list.add(dtoCount);
				} else {
					list = new ArrayList<TrafficQueryDetailsDto>();
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("Total： 0");
					dtoCount.setSessionStartTime(monthDate+"(The Month)");
					list.add(dtoCount);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 剩余查询流量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doNowTrafficQuery")
	public ModelAndView doNowTrafficQuery(HttpServletRequest request,HttpServletResponse response) {
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		ModelAndView mv = new ModelAndView("/traffic/trafficQueryByEnglish");
		String iccid = request.getParameter("iccid");
		if (iccid.length() == 19 && "89860616".equals(iccid.substring(0, 8))) {
			iccid = ICCID.replaceLastChar(iccid);
		}
		if(!ActionUtil.ifSuperUser(request)&&!trafficQueryService.bIsLegalIccId(iccid,custId)){//不是已登陆客户或者其渠道发展客户，不展示剩余流量
			 mv.addObject("trafficDto", null);
			 mv.addObject("iccid", iccid);
			 mv.addObject("expMsg", "你没有权限查询该ICCID的剩余流量信息！");
			 return mv;	
		}
		TrafficQueryNowDto trafficDto = null;
		trafficDto = trafficQueryService.doNowTrafficQuery(iccid);
		String expMsg = null ;
		if (trafficDto != null) {
			expMsg = trafficDto.getExpMsg();
		}
		mv.addObject("trafficDto", trafficDto);
		mv.addObject("iccid", iccid);
		if ("该用户不存在，请确认ICCID是否正确！".equals(expMsg)) {
			expMsg = "The user does not exist. Please make sure that its ICCID is correct！";
		} else if ("调用查询接口异常！".equals(expMsg)){
			expMsg = "Call query interface exception!";
		}
		
		mv.addObject("expMsg", expMsg);
		return mv;
	}

}
