package com.urt.web.web.glaH5;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.interfaces.unicom.QueryBalanceService;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.StringUtil;
/**
 * 类说明：查询服务
 * 
 * @author sunhao
 * @date 2016年8月24日14:31:47
 */
@Controller
@RequestMapping("/glaH5AppQuery")
public class GlaH5AppQueryController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private QueryBalanceService balanceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrafficQueryService trafficQueryService;
	
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	
	@Autowired
	private TradeService tradeService;
	
	@Value("${deviceid}")
	private String deviceid;

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response, String iccid) {
		ModelAndView mv = new ModelAndView("/newH5/queryView");
		//登陆之后，查询gla用户绑定的iccid
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		if(user != null){
			Long custId = user.getCustId();
			if(custId != null){
				List<String> iccidList = userService.getIccidByCustId(custId);
				request.getSession().setAttribute("glaIccidList",iccidList);
			}
		}
		return mv;
	}

	// 查询流量余额
	@RequestMapping("/queryFlow")
	public void queryFlow(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, @RequestParam(value="iccid", required = false)String iccid) {
		PrintWriter out;
		if(StringUtils.isNotBlank(iccid) && iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		try {
			out = response.getWriter();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			boolean flag= false;
			LaoUserDto laoUserDtoByIccid = userService.getLaoUserDtoByIccid(iccid);
			if(laoUserDtoByIccid != null){
				flag = true;
			}
			if(flag){ //如果是gla系统的用户，走的是gla系统的查询流量方法
				//先判断用户是否拥有这张卡的操作权限   然后判断用户是否可以订购这个产品  然后判断用户是否可以订购这个产品  
				String custid = null;
				LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
				if(user != null) {
					if(user.getCustId() != null)
						custid = user.getCustId().toString();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(userService.hasPermission(iccid, custid)){
					TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
					if(doNowTrafficQuery != null){
						resultMap.put("nacid", doNowTrafficQuery.getState());
						List<PackagePlanDto> listPack = doNowTrafficQuery.getListPack();
						if(listPack != null && listPack.size() > 0){
							PackagePlanDto packagePlanDto = listPack.get(0);
							if(StringUtil.isNotBlank(packagePlanDto.getPackageCount()) && StringUtil.isNotBlank(packagePlanDto.getPackageRemain())){
								BigDecimal packageCount = new BigDecimal(packagePlanDto.getPackageCount().replace("M", ""));
								BigDecimal packageRemain=new BigDecimal(packagePlanDto.getPackageRemain().replace("M", ""));
								packageCount = packageCount.subtract(packageRemain);
								resultMap.put("packageCount", packageCount.toString());
								
								BigDecimal dataRemaining = new BigDecimal(doNowTrafficQuery.getDataRemaining().replace("M", ""));
								resultMap.put("dataRemaining", dataRemaining.subtract(packageRemain));
							}else{
								resultMap.put("packageCount", 0);
							}
							resultMap.put("packagePlanName", packagePlanDto.getPackageName());
							resultMap.put("packageRemaining",packagePlanDto.getPackageRemain().replace("M", ""));
							resultMap.put("packageExpirationDate",packagePlanDto.getPackageEndTime());
							resultMap.put("packageNum", listPack.size()-1);
						}else{
							resultMap.put("packagePlanName", 0);
							resultMap.put("packageRemaining",0);
							resultMap.put("packageExpirationDate",sdf.format(new Date()));
							resultMap.put("packageNum", 0);
							resultMap.put("packageCount", 0);
							resultMap.put("dataRemaining", doNowTrafficQuery.getDataRemaining());
						}
						resultMap.put("iccid", iccid);
						resultMap.put("timestamp", sdf.format(new Date()));
						resultMap.put("retcode", "1");
					}else{
						log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>trafficQueryService.doNowTrafficQuery(iccid):"+"查询为空"+iccid);
					}
				}else{
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>custid:"+custid+"没有权限"+iccid);
					resultMap.put("retcode", "-10");
				}
			}
			
			Object jsonObject = JSONObject.toJSON(resultMap);
			out.write(jsonObject.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/queryRecord")
	public ModelAndView queryRecord(String iccid) {
		ModelAndView mv = new ModelAndView("/newH5/queryRecord");
		if(StringUtils.isNotBlank(iccid) && iccid.length() < 20){
			iccid = userService.getIccid(iccid);
		}
		mv.addObject("iccid", iccid);
		return mv;
	}
	
	// 查询充值记录
	@RequestMapping("/queryChargeRecord")
	public ModelAndView queryChargeRecord(HttpServletRequest request, String iccid) {
		ModelAndView mv = new ModelAndView("/newH5/chargeRecord");
		if(iccid != null && iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		if(StringUtils.isNotBlank(iccid) && iccid.length() < 20){
			iccid = userService.getIccid(iccid);
		}
		
		boolean flag= true;
		IccidLibDto dto=userService.selectByIccid(iccid);
		if(dto == null){
			flag = false;
		}
		if(flag){ 										//GLA 功能
			//先判断用户是否拥有这张卡的操作权限   然后判断用户是否可以订购这个产品  然后判断用户是否可以订购这个产品  
			String custid = null;
			LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			if(user != null) {
				if(user.getCustId() != null){
					custid = user.getCustId().toString();
				}
				
				if(userService.hasPermission(iccid, custid)){
					int total = tradeFeeSubService.count(iccid, null, null);
					List<TradeFeeSubDto> tradeFeeList = tradeFeeSubService.queryTradeFreeSubByIccid(iccid, null, null, 1, total);
					mv.addObject("tradeFeeList", tradeFeeList);
				}else{
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>custid:"+custid+"没有权限"+iccid);
				}
			}
		}
		mv.addObject("search", iccid);
		return mv;
	}
	
	// 查询充值记录详情
		@RequestMapping("/queryChargeRecordDetail")
		public ModelAndView queryChargeRecordDetail(String tradeId) throws Exception {
			ModelAndView mv = new ModelAndView("/newH5/chargeRecordDetail");
			TradeFeeSubDto tradeFeeSub = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			if(tradeFeeSub != null){
				GoodsDto good = goodsService.findByGoodsId(tradeFeeSub.getGoodsId());
				 TradeDto trade = tradeService.queryTradeId(tradeFeeSub.getTradeId());
				 if(trade != null){
					 GoodsReleaseDto goodsRealse = goodsReleaseService.findBygoodsReleaseId(trade.getGoodsReleaseId());
					 if(goodsRealse != null){
						 good.setUnit(goodsRealse.getUnit());
						 good.setReleaseCycle(goodsRealse.getReleaseCycle());
					 }
					 mv.addObject("shebei", trade.getIccid());
				 }
				 tradeFeeSub.setGoodsDto(good);
			}
			mv.addObject("tradeFee", tradeFeeSub);
			return mv;
		}

	// 查询消费记录
	@RequestMapping("/queryPurchaseHistory")
	@ResponseBody
	public String queryPurchaseHistory(HttpServletRequest request, String iccid, String startDate, String endDate) {
		JSONArray jsonArray = new JSONArray();
		if(iccid != null && iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		boolean flag= true;
		IccidLibDto dto=userService.selectByIccid(iccid);
		if(dto == null){
			flag = false;
		}
		if(flag){                                          //gla 查询消费记录
			String custid = null;
			LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			if(user != null) {
				if(user.getCustId() != null){
					custid = user.getCustId().toString();
					if(userService.hasPermission(iccid, custid)){
						List<TrafficQueryDetailsDto> list = new ArrayList<>();
					 	Date date;
						try {
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
							Calendar start = Calendar.getInstance();  
							date = df.parse(startDate);
						 	start.setTime(date);
						    Long startTIme = start.getTimeInMillis();  
						  
						    Calendar end = Calendar.getInstance();  
						    date = df.parse(endDate);  
						 	end.setTime(date);
						    Long endTime = end.getTimeInMillis();  
						  
						    Long oneDay = 1000 * 60 * 60 * 24l;  
						  
						    Long time = startTIme;  
						    while (time <= endTime) {  
						        Date d = new Date(time);  //df.format(d)
						        List<TrafficQueryDetailsDto> dayList = trafficQueryService.doDayTrafficQuery(iccid, df.format(d));
						        if(dayList != null && dayList.size() > 0){
//						        	for (TrafficQueryDetailsDto trafficQueryDetailsDto : dayList) {
//									}
						        	list.add(dayList.get(dayList.size() - 1));
						        }
						        time += oneDay;  
						    } 
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						for (TrafficQueryDetailsDto tdto : list) {
							JSONObject result = new JSONObject();
							result.put("employ", tdto.getDataVolume());
							result.put("continued", tdto.getDuration());
							result.put("dateTime", tdto.getSessionStartTime());
							result.put("package", tdto.getZone());
							jsonArray.add(result);
						}
						
					}else{
						log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>custid:"+custid+"没有权限"+iccid);
					}
				}
			}
		}
		return jsonArray.toJSONString();
	}
}
