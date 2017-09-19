package com.urt.web.web.unicom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.interfaces.unicom.QueryBalanceService;
import com.urt.web.common.util.JsonConverter;
import com.urt.web.common.util.MD5;
import com.urt.web.common.util.StringUtil;
/**
 * 类说明：查询服务
 * 
 * @author sunhao
 * @date 2016年8月24日14:31:47
 */
@Controller
@RequestMapping("/queryService")
public class QueryBalanceController {
	@Autowired
	private QueryBalanceService balanceService;
	@Value("${deviceid}")
	private String deviceid;
	@Autowired
	private DeviceService deviceService;
	
	private final static int pageSize = 5;

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("/H5/balance");
		if(session.getAttribute("deviceList") == null){
			String userId = (String) session.getAttribute("lenovoid");
			reloadIccid(userId,session);
		}
		return mv;
	}

	// 查询流量余额
	@RequestMapping("/queryFlow")
	public void queryFlow(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		PrintWriter out;
		try {
			out = response.getWriter();
			DeviceDto device = new DeviceDto();
			String iccid = request.getParameter("iccid");
			session.setAttribute("iccid", iccid);
			device.setIccid(iccid);
			String lpsust=(String) session.getAttribute("lpsust");
			String secretKey= MD5.md5UTF8(MD5.md5UTF8(device.getIccid() + deviceid + lpsust));
			device.setDeviceId(deviceid);
			device.setSecretKey(secretKey);
			device.setLpsust(lpsust);
			
			Map<String, Object> map = balanceService.queryFlow(device);
			Object jsonObject = JSONObject.toJSON(map);
			out.write(jsonObject.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/queryRecord")
	public ModelAndView queryRecord() {
		ModelAndView mv = new ModelAndView("/H5/queryRecord");
		return mv;
	}
	
	// 查询充值记录
	@RequestMapping("/queryChargeRecord")
	public ModelAndView queryChargeRecord(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView("/H5/chargeRecord");
		String userId = (String) session.getAttribute("lenovoid");
		int curPage = request.getParameter("curPage") == null ? 1:Integer.parseInt(request.getParameter("curPage"));
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String,Object> map = balanceService.findChargeOrderPage( startTime, endTime,  userId,  curPage, pageSize);
		mv.addObject("list", map.get("list"));
		mv.addObject("totalPage", map.get("totalPage"));
		mv.addObject("curPage", curPage);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		return mv;
	}
	
	// 查询充值记录详情
		@RequestMapping("/queryChargeRecordDetail")
		public ModelAndView queryChargeRecordDetail(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mv = new ModelAndView("/H5/chargeRecordDetail");
			String iccid = request.getParameter("iccid");
			String username = request.getParameter("username");
			String orderid = request.getParameter("orderid");
			String flowsize= request.getParameter("flowsize");
			String payamount= request.getParameter("payamount");
			String paytype= request.getParameter("paytype");
			String chargedate = request.getParameter("chargedate");
			
			mv.addObject("iccid", iccid);
			mv.addObject("username", username);
			mv.addObject("orderid", orderid);
			mv.addObject("flowsize", flowsize);
			mv.addObject("payamount", payamount);
			if(!StringUtil.isBlank(paytype) ){
				if(paytype.equals("1")){
					paytype = "支付宝";
				}
				else if(paytype.equals("0")){
					paytype = "充值赠送";
				}
			}
			mv.addObject("paytype", paytype);
			mv.addObject("chargedate", chargedate);
			return mv;
		}

	// 查询消费记录
	@RequestMapping("/queryPurchaseHistory")
	public ModelAndView queryPurchaseHistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView("/H5/purchaseHistory");
		DeviceDto device = new DeviceDto();
		String iccid = (String) session.getAttribute("iccid");
		device.setIccid(iccid);
		String lpsust=(String) session.getAttribute("lpsust");
		String secretKey= MD5.md5UTF8(MD5.md5UTF8(device.getIccid() + deviceid + lpsust));
		device.setDeviceId(deviceid);
		device.setSecretKey(secretKey);
		device.setLpsust(lpsust);
		
		String accountId = (String) session.getAttribute("lenovoid");
		String curPage = request.getParameter("curPage") == null ? "1":request.getParameter("curPage");
		Map<String,Object> map = balanceService.queryPurchaseHistory(device,accountId,curPage);
		mv.addObject("list", map.get("list"));
		mv.addObject("curPage", curPage);
		return mv;
	}
	
	/**
	 * 查询用户绑定的iccid卡号和设备信息
	 * @param user
	 * @param session
	 */
	public void reloadIccid(String lenovoid,HttpSession session){
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+lenovoid);
		Map<String, Object> map = deviceService.queryLenovoidStatus(lenovoid);
//		String statusCode = (String) map.get("retcode");
//		session.setAttribute("simCardStatus",statusCode);
		Object obj=map.get("iccidList");
		if(null!=obj){
			Object[] deviceList=JsonConverter.jsonToArray(obj);
			session.setAttribute("deviceList",deviceList);
		}
	}
}
