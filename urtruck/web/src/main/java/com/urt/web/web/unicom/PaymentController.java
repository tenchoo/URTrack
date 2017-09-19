package com.urt.web.web.unicom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.PayService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.unicom.PaymentService;
import com.urt.web.common.util.AccountCookie;
import com.urt.web.http.device.PayCallBackController;


/**
 * 类说明：充值服务
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/paymentService")
public class PaymentController {
	private static final Logger log=Logger.getLogger(PayCallBackController.class);
	private static final String SERVERNAME="device+ windows支付回调";
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private PayService payService;
	@Autowired
	private ServerCheckService serverService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	
	/**
	 * 进入到充流量页面
	 * @return
	 */
	@RequestMapping("/topay")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/H5/charge");
		AccountCookie accountCookie = new AccountCookie(request,response);
		accountCookie = accountCookie.getCookieAccount();
		
		String userId = accountCookie.getUuid();
		String userName = accountCookie.getUname();
		Map<String,Object> map = paymentService.getPaymentInfo(userId);//userid
		
		mv.addObject("userInfoList", map.get("userInfoList"));
		mv.addObject("flowConfigList", map.get("flowConfigList"));
		mv.addObject("userName", userName);//userName
		return mv;
	}
	
	/**
	 * 跳转确认支付界面
	 * @return
	 */
	@RequestMapping("/toComfirm")
	public ModelAndView  aliPay(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView("/H5/toalipay");
		String iccid = request.getParameter("iccid");
		String flowSize = request.getParameter("flowSize");
		Double payAmount = Double.parseDouble(request.getParameter("payAmount"));
		mv.addObject("iccid", iccid);
		mv.addObject("flowSize", flowSize);
		mv.addObject("payAmount",payAmount);
		return mv;
	}
	
	/**
	 * 支付，创建订单
	 * @return
	 */
	@RequestMapping("/torealPay")
	public ModelAndView  toaliPay(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView("/H5/pay");
		AccountCookie accountCookie = new AccountCookie(request,response);
		accountCookie = accountCookie.getCookieAccount();
		String userId = accountCookie.getUuid();
		String userName = accountCookie.getUname();
		String lpsust=(String) session.getAttribute("lpsust");
		
		String iccid = request.getParameter("iccid");
		String flowSize = request.getParameter("flowSize");
		Double payAmount = Double.parseDouble(request.getParameter("payAmount"));
		
		Map<String,String> map = paymentService.toAliPay(userId, userName, flowSize, payAmount, iccid, lpsust);
		if(map == null || map.size() < 0){
			mv = new ModelAndView("redirect:/paymentService/topay");
		}else{
			mv.addObject("pcwebPayRequestUrl", map.get("privateAttach"));
			map.put("privateAttach", "");      //将privateAttach清空
			mv.addObject("params", map);
		}
		return mv;
	}
	
	/**
	 * 支付完回调函数
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/aliPayCallBack")
	public ModelAndView  aliPayCallBack(HttpServletRequest request, HttpServletResponse response, HttpSession session,PaymentDto payment) throws NumberFormatException, Exception{
		log.info(">>>>>>>>>>>>>>>enter the method device+ aliPayCallBack<<<<<<<<<<<<<<<");
		System.out.println("接收到的值是："+payment.toString());
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setServerName(SERVERNAME);
//				int orderStatus = backDto.getOrderStatus();
//				if(orderStatus==1){
//					recordDto.setIsSuccess("0");
//				}else{
//					recordDto.setIsSuccess("1");
//				}
//						JSONObject reqJson=new JSONObject();
		recordDto.setReqInfo(payment.toString());
//				recordDto.setRspInfo(issuccess);
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");

		String orderId = payment.getMerchantOrderId();
		TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(orderId));
		TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeDto.getTradeId().toString());
		if(tradeDto != null){
			ModelAndView mv = new ModelAndView("Goods/paySuccess");
			Map<String, Object> result = payService.aliPayCallBacktMap(payment);
			recordDto.setRspInfo(result.toString());
			Boolean flag = (Boolean) result.get("recode");
			if(!flag){
				mv = new ModelAndView("Goods/payFaile");
				recordDto.setIsSuccess("1");
				if ("1".equals(tradeFeeSubDto.getPayTag())) {
					mv = new ModelAndView("Goods/paySuccess");
					recordDto.setIsSuccess("0");
				}else{
					mv = new ModelAndView("Goods/payFaile");
					recordDto.setIsSuccess("1");
				}
			}else{
				recordDto.setIsSuccess("0");
				
			}
			mv.addObject("iccid",  result.get("iccid"));
			mv.addObject("payAmount", result.get("payAmount"));
			
			log.info(">>>>>>>>>>>exit the method device+ payBack<<<<<<<<<<<<<<<<<<<");
			serverService.savaLogerToDb(recordDto);
			//小懂上网2C的回调到充值查询记录页面
            log.info("============================TradeTypeCode="+tradeDto.getTradeTypeCode());
			if ("150".equals(tradeDto.getTradeTypeCode()+"")) {
				mv = new ModelAndView("redirect:../customerQuery/toRecharge");
				String chargeMsg = "充值失败！";
				if(!flag){
					if ("1".equals(tradeFeeSubDto.getPayTag())) {
						chargeMsg = "充值成功！";
						mv = new ModelAndView("redirect:../customerQuery/toRateSearch2");
					}
				} else {
					chargeMsg = "充值成功！";
					mv = new ModelAndView("redirect:../customerQuery/toRateSearch2");
				}
				mv.addObject("chargeMsg", chargeMsg);
				mv.addObject("iccid", result.get("iccid"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String today = sdf.format(new Date());
				mv.addObject("startTime", today);
				mv.addObject("endTime", today);
				mv.addObject("currentPage", 1);
			}
			return mv;
		}else{
			ModelAndView mv = new ModelAndView("/H5/paySuccess");
			Map<String,Object> result = paymentService.aliPayCallBack(payment);
			Boolean flag = (Boolean) result.get("recode");
			if(!flag){
				mv = new ModelAndView("/H5/payFail");
			}
			mv.addObject("iccid",  result.get("iccid"));
			mv.addObject("flowSize", result.get("flowSize"));
			mv.addObject("payAmount", result.get("payAmount"));
			log.info(">>>>>>>>>>>exit the method device+ payBack<<<<<<<<<<<<<<<<<<<");
			return mv;
		}
	}
	
}
