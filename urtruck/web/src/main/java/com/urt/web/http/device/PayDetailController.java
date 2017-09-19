package com.urt.web.http.device;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Trade.TradeDiscontDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.http.device.DeviceProductService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 缴费详情
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/detail")
public class PayDetailController {
	
	private static final Logger log=Logger.getLogger(PayDetailController.class);
	private static final String SERVERNAME="device+缴费详情";
	private static final String DETAILURL="http://h5mobiletest.lenovomm.com/customerQuery/toRateSearch";
	
	@Autowired
	private ServerCheckService serverService;
	@Autowired
	private DeviceProductService deviceService;
	@Autowired
	private DeviceService deviceServiceII;
	
	@RequestMapping(value = "paydetailurl", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String paydetailurl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("enter the method paydetailurl");
		String retCode = "1";//-1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
		
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("date", new Date());
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method paydetailurl");
		return DETAILURL;
	}
	@RequestMapping(value = "topaydetail", method = { RequestMethod.POST,RequestMethod.GET})
	public ModelAndView topaydetail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "st", required = true) String st
			) throws Exception {
		log.info("enter the method topaydetail");
		ModelAndView mv = new ModelAndView("device/paydetail");
		iccid = iccid.replace("#", "B");
		Account account = deviceServiceII.authSt(st);
		if(!StringUtil.isBlank(account.getAccountID())&&!deviceServiceII.lenovoIdCheck(account.getAccountID())){
			//如果此accountid在库中没有 需要 默认添加 数据到 lao_customer lao_ss_account lao_cust_person lao_account_rel 
			 String custIdPerson = deviceServiceII.initPersonCustomer(account.getAccountID(),account.getUsername());
			if(StringUtil.isBlank(custIdPerson)){
				throw new Exception("生成用户报错");
			}		
		}
		
		mv.addObject("iccid", iccid);
		log.info("exit the method topaydetail");
		return mv;
	}
	@RequestMapping(value = "paydetail", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Map<String, Object> paydetail(
			HttpServletRequest request,
			HttpServletResponse response,
			TradeDto tradeDto
			) throws Exception {
		log.info("enter the method paydetail");
		int pageStart = Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
        int pageSize = Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
        int pageNo = (pageStart / pageSize)+1;//第几页
        String sEcho = request.getParameter("sEcho");
        Map<String, Object> resultMap=deviceService.selectPayDetailByIccid(pageSize, pageNo, tradeDto);
        resultMap.put("sEcho", sEcho);
		log.info("exit the method paydetail");
		return resultMap;
	}
	
}
